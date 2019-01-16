package com.gy.oss;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.gy.exception.UploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

/**
 * @描述: 阿里云OSS文件服务器工具类
 * @作者: DuKai
 * @创建时间: 2018/12/14 15:48
 * @版本号: V1.0
 */
public class OssUtil {

	private static final Logger log = LoggerFactory.getLogger(OssUtil.class);
	private static OssConfig ossInfo = null;
	private static final String sperator = "/";
	private static OSSClient client  = null;


	private static class OssUtilHandler {
		private static OssUtil instance = new OssUtil();
	}

	public static OssUtil getInstance() {
		return OssUtil.OssUtilHandler.instance;
	}

	public void init(OssConfig ossInfo) {
		client = new OSSClient(ossInfo.getEndpoint(), ossInfo.getAccessKeyId(), ossInfo.getAccessKeySecret());
		OssUtil.ossInfo = ossInfo;
		log.info("-----------------------oss文件服务器工具加载完成");
	}
	
	public static void destroy() {
		disconnect();
	}

	/**
	 * OSS连接
	 * @return
	 */
	private static OSSClient connect() {
		if(client == null) {
			client = new OSSClient(ossInfo.getEndpoint(), ossInfo.getAccessKeyId(),
					ossInfo.getAccessKeySecret());
		}
		return client;
	}

	/**
	 * 关闭连接
	 * @return
	 */
	public static boolean disconnect() {
		if (client != null) {
			client.shutdown();
			client = null;
			return true;
		}
		return false;
	}

	/**
	 * 文件上传
	 * @param fileSrc 文件路径
	 * @param bucket 根目录名称
	 * @param buf
	 * @return
	 */
	public static boolean upload(String bucket, String fileSrc, byte[] buf) {
		return upload(bucket, fileSrc, new ByteArrayInputStream(buf));
	}

	public static boolean upload(String bucket, String fileSrc, File file) {
		try {
			return upload(bucket, fileSrc, new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new UploadException(e.getMessage(), e);
		}
	}

	private static boolean upload(String bucket, String path, InputStream is) {
		Assert.hasText(path, "路径不能为空");
		log.debug(" --------------------- 上传路径 ::::: " + path);
		Assert.hasText(bucket, "Bucket不能为空");
		log.debug(" --------------------- 上传BucketName ::::: " + bucket);
		Assert.notNull(is, "路径不能为空");

		boolean flag = false;
		OSSClient ossClient = connect();
		try {
			ossClient.putObject(bucket, path, is);
			flag = true;
		} catch (Exception e) {
			throw new UploadException(e.getMessage(), e);
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return flag;
	}

	/**
	 * 多文件上传
	 * @param fileList
	 * @return
	 */
	public static List<String> uploadMore(List<FileUploadInfo> fileList) {
		List<String> result = new ArrayList<String>();
		if (fileList == null) {
			log.error("---------------------- 上传文件列表为空");
			return result;
		}

		OSSClient ossClient = connect();
		ByteArrayInputStream bis = null;

		for (FileUploadInfo fi : fileList) {
			String bucket = fi.getBucket();
			String fileSrc = fi.getFileSrc();
			byte[] fileByteArray = fi.getFileByteArray();
			try {
				bis = new ByteArrayInputStream(fileByteArray);
				ossClient.putObject(bucket, fileSrc, bis);
				result.add(fileSrc);
				try {
					if (bis != null) {
						bis.close();
						bis = null;
					}
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			} catch (Exception e) {
				log.error("-------------------- 文件：" + fileSrc + "上传失败", e);
			} finally {
				try {
					if (bis != null) {
						bis.close();
						bis = null;
					}
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return result;
	}

	/**
	 * 删除文件
	 * @param bucket
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(String bucket, String fileName) throws UploadException {
		boolean flag = false;
		OSSClient ossClient = connect();
		// 删除Object
		try {
			ossClient.deleteObject(bucket, fileName);
			flag = true;
		} catch (OSSException | ClientException e) {
			throw new UploadException(e.getMessage(), e);
		}
		return flag;
	}

	public static boolean deleteFiles(String bucket, List<String> fileNames) {
		boolean flag = false;
		OSSClient ossClient = connect();
		// 删除Object
		try {
			ossClient.deleteObjects(new DeleteObjectsRequest(bucket).withKeys(fileNames));
			flag = true;
		} catch (OSSException | ClientException e) {
			throw new UploadException(e.getMessage(), e);
		}
		return flag;
	}

	/**
	 * 获取对应文件夹下所有文件名
	 * @param folderName
	 * @return
	 */
	public static List<String> getAllFileName(String bucket, String folderName) throws UploadException {
		OSSClient ossClient = connect();
		List<String> list = new ArrayList<String>();
		try {
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucket);
			// 设置文件名
			if (StringUtils.hasLength(folderName)) {
				listObjectsRequest.setPrefix(folderName + sperator);
			}

			// 递归列出fun目录下的所有文件
			ObjectListing listing = ossClient.listObjects(listObjectsRequest);
			// 遍历所有Object
			for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
				System.out.println(objectSummary.getKey());
				list.add(objectSummary.getKey());
			}
		} catch (OSSException | ClientException e) {
			throw new UploadException(e.getMessage(), e);
		}
		return list;
	}

	/**
	 * 创建文件夹
	 * @param folderName
	 * @return
	 * @throws OSSException
	 * @throws ClientException
	 */
	public static boolean createFolder(String bucket, String folderName) throws UploadException {
		boolean flag = false;
		OSSClient ossClient = connect();
		try {
			final String keySuffixWithSlash = folderName;
			ossClient.putObject(bucket, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
			flag = true;
		} catch (OSSException | ClientException e) {
			throw new UploadException(e.getMessage(), e);
		}
		return flag;
	}

	/**
	 * 从Temp文件夹复制到相对应的路径
	 * @param oldFilePath Temp文件夹路径名
	 * @param newFilePath 将要上传的路径名
	 * @return
	 */
	public static boolean copyFile(String oldBucket, String oldFilePath, String newBucket, String newFilePath)
			throws UploadException {
		boolean flag = false;
		OSSClient ossClient = connect();
		try {
			// 拷贝Object到新的地址
			ossClient.copyObject(oldBucket, oldFilePath, newBucket, newFilePath);
			// ossClient.deleteObject(ossInfo.getTempBucket(), oldFilePath);//
			// 删除temp文件
			flag = true;
		} catch (Exception e) {
			throw new UploadException(e.getMessage(), e);
		}
		return flag;
	}

}
