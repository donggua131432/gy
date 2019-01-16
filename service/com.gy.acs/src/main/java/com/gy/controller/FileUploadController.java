package com.gy.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.gy.conf.GySysConfig;
import com.gy.domain.ReturnInfo;
import com.gy.domain.FileInfo;
import com.gy.domain.dto.sys.DictInfo;
import com.gy.enums.ErrorCodeEnum;
import com.gy.exception.CustomException;
import com.gy.oss.FileUploadInfo;
import com.gy.core.util.FileUploadUtil;
import com.gy.service.sys.DictService;
import com.gy.util.CodeUtil;
import com.gy.util.DateUtil;
import com.gy.util.JsonUtil;
import com.gy.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;

@RestController
@Api(tags = "文件上传接口",description = "FileUpload-API")
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	@Autowired
	private GySysConfig gySysConfig;

	@Autowired
	private DictService dictService;

	/**
	 * 单文件上传
	 * @param request
	 * @return
	 * @throws Throwable
	 */
	@ApiOperation(value = "单文件上传", notes = "单文件上传")
	@PostMapping("/uploadFile")
	public ReturnInfo<FileInfo> uploadFile(@RequestParam("file") MultipartFile multipartFile, MultipartHttpServletRequest request){
		String contentType = multipartFile.getContentType();   //图片文件类型
		String fileName = multipartFile.getOriginalFilename();  //图片名字
		int fileSize = (int) (multipartFile.getSize()/1024); //转换为KB
		String suffix = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
		String dataStr = DateUtil.getCurrentDate("yyyy-MM-dd");
		String[] dataArr = dataStr.split("-");
		String filePath = gySysConfig.getFilePath()+"/"+dataArr[0]+"/"+dataArr[1]+"/"+dataArr[2]+"/"+fileName;
		try {
			String bucket = gySysConfig.getBucket();
			boolean uploadFlag = FileUploadUtil.upload(bucket, filePath, multipartFile.getBytes());
			if(uploadFlag){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setFileName(fileName);
				fileInfo.setFileSuffix(suffix);
				fileInfo.setFilePath(filePath);
				fileInfo.setFileSize(fileSize);
				if(contentType.contains("video")){
					final File tempFile = File.createTempFile(StringUtil.UUID(),suffix);
					multipartFile.transferTo(tempFile);
					//创建媒体对象
					MultimediaObject multimediaObject = new MultimediaObject(tempFile);
					MultimediaInfo multimediaInfo = multimediaObject.getInfo();
					//获取视频文件的播放时长换为秒
					int playTime = (int) (multimediaInfo.getDuration()/1000);
					fileInfo.setPlayTime(playTime);
					tempFile.deleteOnExit();
				}
				return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(fileInfo);
			}
		} catch (Exception e) {
			logger.error("文件转换出现异常，上传失败：{}",e);
		}
		return ReturnInfo.getInstance().setResult(ErrorCodeEnum.UPLOAD_ERROR).setData(null);
	}


	@ApiOperation(value = "生成数据字典Json文件", notes = "生成数据字典Json文件")
	@GetMapping("/uploadDict")
	public ReturnInfo uploadDict(){
		List<DictInfo> dictInfoList = dictService.listDictInfo();
		String jsonStr = JsonUtil.objectToString(dictInfoList);
		String bucket = gySysConfig.getBucket();
		String filePath = gySysConfig.getFilePath()+"/dict.json";
		try {
			FileUploadUtil.upload(bucket, filePath, jsonStr.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("数据字典文件上传异常：{}",e);
			return ReturnInfo.getInstance().setResult(ErrorCodeEnum.UPLOAD_ERROR).setData(null);
		}
		return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(null);
	}


	/**
	 * 多文件上传
	 * @param files
	 * @return
	 * @throws Throwable
	 */
	private List<String> uploadFiles(String[] files){
		List<FileUploadInfo> uploadInfos = new ArrayList<FileUploadInfo>();
		String bucket = gySysConfig.getBucket();
		for (String file : files) {
			int i = file.indexOf(",");
			String suffix = null;
			String fileData = null;
			if(i > 5) {
				throw new CustomException("请输入正确的文件前缀");
			} else if(i >= 0) {
				suffix = file.substring(0, i);
				fileData = file.substring(i + 1, file.length());
			} else {
				fileData = file;
			}
			String fileId = StringUtil.UUID();
			String filename = fileId;
			if(StringUtil.hasValue(suffix)) {
				suffix = suffix.trim();
				filename = fileId + "." + suffix;// UUID + 前缀
			}
			
			byte[] fileByte = CodeUtil.base64Decode(fileData);
			uploadInfos.add(new FileUploadInfo(bucket, filename, fileByte));
		}
		return FileUploadUtil.uploadMore(uploadInfos);
	}
 
}
