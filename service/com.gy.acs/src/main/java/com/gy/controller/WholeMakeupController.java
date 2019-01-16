package com.gy.controller;

import com.github.pagehelper.PageInfo;
import com.gy.domain.FileInfo;
import com.gy.domain.ResultPage;
import com.gy.domain.ReturnInfo;
import com.gy.domain.dto.mongo.AppCourseZip;
import com.gy.domain.dto.sys.FileInfoRel;
import com.gy.domain.dto.sys.SysFileInfo;
import com.gy.domain.dto.sys.WholeMakeup;
import com.gy.domain.vo.req.makeup.AppCourseZipQuery;
import com.gy.domain.vo.req.makeup.WholeMakeupQuery;
import com.gy.domain.vo.res.WholeMakeupVo;
import com.gy.enums.ErrorCodeEnum;
import com.gy.mongo.MongoPageWrapper;
import com.gy.service.makeup.FileInfoRelService;
import com.gy.service.makeup.TryMakeupService;
import com.gy.service.makeup.WholeMakeupService;
import com.gy.service.sys.SysFileInfoService;
import com.gy.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 整装库接口
 * @作者: DuKai
 * @创建时间: 2018/12/18 17:22
 * @版本号: V1.0
 */
@RestController
@Api(tags={"整装库接口"}, description = "WholeMakeup-API")
@RequestMapping("/wholeMakeup")
public class WholeMakeupController {
    private final static Logger logger = LoggerFactory.getLogger(WholeMakeupController.class);

    @Autowired
    private  WholeMakeupService wholeMakeupService;
    @Autowired
    private TryMakeupService tryMakeupService;
    @Autowired
    private FileInfoRelService fileInfoRelService;

    @ApiOperation(value = "获取试妆包信息", notes = "获取试妆包信息")
    @GetMapping("/getAppCourseZip/{id}")
    public ReturnInfo<AppCourseZip> getAppCourseZip(@ApiParam("试装包Id") @PathVariable String id){
        AppCourseZip appCourseZip = tryMakeupService.getAppCourseZip(id);
        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(appCourseZip);
    }

    @ApiOperation(value = "获取分页试妆包列表", notes = "获取分页试妆包列表")
    @PostMapping("/listAppCourseZip")
    public ReturnInfo<ResultPage<AppCourseZip>> listAppCourseZip(@ApiParam("试装包信息查询条件") @RequestBody AppCourseZipQuery appCourseZipQuery){
        MongoPageWrapper<AppCourseZip> pageInfo = tryMakeupService.listAppCourseZip(appCourseZipQuery);
        ResultPage<AppCourseZip> resultPage = ResultPage.getInstance()
                .setPageNum(pageInfo.getPage())
                .setPages(pageInfo.getTotalPage())
                .setTotal(pageInfo.getTotal())
                .setList(pageInfo.getRows());
        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(resultPage);
    }

    @ApiOperation(value = "分页查询整装库")
    @PostMapping("/listWholeMakeupPage")
    public ReturnInfo<ResultPage<WholeMakeupVo>> listWholeMakeupPage(@ApiParam(name = "WholeMakeupQuery",
            value = "整装库查询对象") @RequestBody WholeMakeupQuery wholeMakeupQuery) {
        PageInfo<WholeMakeupVo> pageInfo = wholeMakeupService.listWholeMakeupPage(wholeMakeupQuery);
        ResultPage<WholeMakeupVo> resultPage = ResultPage.getInstance().setPageNum(pageInfo.getPageNum())
                .setPages(pageInfo.getPages())
                .setTotal(pageInfo.getTotal())
                .setList(pageInfo.getList());
        return new ReturnInfo<WholeMakeupVo>().setResult(ErrorCodeEnum.SUCCESS).setData(resultPage);
    }

    @ApiOperation(value = "添加整装库")
    @PostMapping("/addWholeMakeup")
    @Transactional
    public ReturnInfo addWholeMakeup(@ApiParam(name = "WholeMakeupVo", value = "整装库接收对象", required = true) @RequestBody WholeMakeupVo wholeMakeupVo) {
        //生成整装库ID
        String wholeMakeupId = StringUtil.UUID();
        //添加入口图和模特图信息信息的文件业务关系表信息
        FileInfoRel inleFileInfoRel = new FileInfoRel();
        inleFileInfoRel.setRelId(wholeMakeupId);
        inleFileInfoRel.setFileId(wholeMakeupVo.getFileInfoRelList().get(0).getFileId());
        inleFileInfoRel.setFileType("1");
        inleFileInfoRel.setFileOrder(wholeMakeupVo.getFileInfoRelList().get(0).getFileOrder());
        FileInfoRel coverFileInfoRel = new FileInfoRel();
        coverFileInfoRel.setRelId(wholeMakeupId);
        coverFileInfoRel.setFileId(wholeMakeupVo.getFileInfoRelList().get(1).getFileId());
        coverFileInfoRel.setFileType("2");
        coverFileInfoRel.setFileOrder(wholeMakeupVo.getFileInfoRelList().get(1).getFileOrder());
        List<FileInfoRel> fileInfoRelList = new ArrayList<>();
        fileInfoRelList.add(inleFileInfoRel);
        fileInfoRelList.add(coverFileInfoRel);
        fileInfoRelService.addFileInfoRelBatch(fileInfoRelList);
        //添加整装基本信息
        WholeMakeup wholeMakeup = new WholeMakeup();
        wholeMakeup.setWholeMakeupId(wholeMakeupId);
        wholeMakeup.setWholeMakeupStatus(wholeMakeupVo.getWholeMakeupStatus());
        wholeMakeup.setBrandId(wholeMakeupVo.getBrandId());
        wholeMakeup.setKolId(wholeMakeupVo.getKolId());
        wholeMakeup.setWholeMakeupName(wholeMakeupVo.getWholeMakeupName());
        wholeMakeup.setCoverBackground(wholeMakeupVo.getCoverBackground());
        wholeMakeup.setCoverTitle(wholeMakeupVo.getCoverTitle());
        wholeMakeup.setCoverTitleColour(wholeMakeupVo.getCoverTitleColour());
        wholeMakeup.setInleName(wholeMakeupVo.getInleName());
        int flag = wholeMakeupService.addWholeMakeup(wholeMakeup);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "更新整装库")
    @PutMapping("/updateWholeMakeup")
    public ReturnInfo updateWholeMakeup(@ApiParam(name = "WholeMakeupVo", value = "整装库接收对象", required = true) @RequestBody WholeMakeupVo wholeMakeupVo) {
        //添加入口图和模特图信息信息的文件业务关系表信息
        FileInfoRel inleFileInfoRel = new FileInfoRel();
        inleFileInfoRel.setRelId(wholeMakeupVo.getWholeMakeupId());
        inleFileInfoRel.setFileId(wholeMakeupVo.getFileInfoRelList().get(0).getFileId());
        inleFileInfoRel.setFileType("1");
        inleFileInfoRel.setFileOrder(wholeMakeupVo.getFileInfoRelList().get(0).getFileOrder());
        FileInfoRel coverFileInfoRel = new FileInfoRel();
        coverFileInfoRel.setRelId(wholeMakeupVo.getWholeMakeupId());
        coverFileInfoRel.setFileId(wholeMakeupVo.getFileInfoRelList().get(1).getFileId());
        coverFileInfoRel.setFileType("2");
        coverFileInfoRel.setFileOrder(wholeMakeupVo.getFileInfoRelList().get(1).getFileOrder());
        List<FileInfoRel> fileInfoRelList = new ArrayList<>();
        fileInfoRelList.add(inleFileInfoRel);
        fileInfoRelList.add(coverFileInfoRel);
        fileInfoRelService.updateFileInfoRelBatch(fileInfoRelList);
        //添加整装基本信息
        WholeMakeup wholeMakeup = new WholeMakeup();
        wholeMakeup.setWholeMakeupId(wholeMakeupVo.getWholeMakeupId());
        wholeMakeup.setWholeMakeupStatus(wholeMakeupVo.getWholeMakeupStatus());
        wholeMakeup.setBrandId(wholeMakeupVo.getBrandId());
        wholeMakeup.setKolId(wholeMakeupVo.getKolId());
        wholeMakeup.setWholeMakeupName(wholeMakeupVo.getWholeMakeupName());
        wholeMakeup.setCoverBackground(wholeMakeupVo.getCoverBackground());
        wholeMakeup.setCoverTitle(wholeMakeupVo.getCoverTitle());
        wholeMakeup.setCoverTitleColour(wholeMakeupVo.getCoverTitleColour());
        wholeMakeup.setInleName(wholeMakeupVo.getInleName());
        int flag = wholeMakeupService.updateWholeMakeup(wholeMakeup);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "删除整装库")
    @DeleteMapping("/deleteWholeMakeup/{wholeMakeupId}")
    public ReturnInfo deleteWholeMakeup(@ApiParam(name = "wholeMakeupId", value = "整装库ID", required = true) @PathVariable String wholeMakeupId) {
        WholeMakeup wholeMakeup = new WholeMakeup();
        wholeMakeup.setWholeMakeupId(wholeMakeupId);
        wholeMakeup.setWholeMakeupStatus("0");
        wholeMakeupService.updateWholeMakeup(wholeMakeup);
        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(null);
    }
}
