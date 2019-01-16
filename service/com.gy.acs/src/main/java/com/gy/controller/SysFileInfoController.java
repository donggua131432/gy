package com.gy.controller;

import com.github.pagehelper.PageInfo;
import com.gy.domain.ResultPage;
import com.gy.domain.ReturnInfo;
import com.gy.domain.dto.sys.SysFileInfo;
import com.gy.domain.vo.req.SysFileInfoQuery;
import com.gy.enums.ErrorCodeEnum;
import com.gy.service.makeup.VideoSubService;
import com.gy.service.sys.SysFileInfoService;
import com.gy.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @描述: 文件接口
 * @作者: FangLin
 * @创建时间: 2018/12/21 11:56
 * @版本号: V1.0
 */
@RestController
@Api(tags = "系统管理接口",description = "Sys-API")
@RequestMapping("/sysFileInfo")
public class SysFileInfoController {
    @Autowired
    private SysFileInfoService sysFileInfoService;
    @Autowired
    private VideoSubService videoSubService;

    @ApiOperation(value = "分页查询文件信息")
    @PostMapping("/listSysFileInfo")
    public ReturnInfo<ResultPage<SysFileInfo>> listSysFileInfo(@ApiParam(name = "SysFileInfoQuery", value = "文件分页查询实体") @RequestBody SysFileInfoQuery sysFileInfoQuery) {
        PageInfo<SysFileInfo> pageInfo = sysFileInfoService.listSysFileInfoPage(sysFileInfoQuery);
        ResultPage<SysFileInfo> resultPage = ResultPage.getInstance().setPageNum(pageInfo.getPageNum())
                .setPages(pageInfo.getPages())
                .setTotal(pageInfo.getTotal())
                .setList(pageInfo.getList());
        return new ReturnInfo().setResult(ErrorCodeEnum.SUCCESS).setData(resultPage);
    }

    @ApiOperation(value = "添加文件信息")
    @PostMapping("/addSysFileInfo")
    public ReturnInfo addSysFileInfo(@ApiParam(name = "SysFileInfo", value = "文件对象", required = true) @RequestBody SysFileInfo sysFileInfo) {
        String fileId = StringUtil.UUID();
        sysFileInfo.setFileId(fileId);
        int flag = sysFileInfoService.addSysFileInfo(sysFileInfo);
        if(flag>0){
            return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(fileId);
        }else{
            return ReturnInfo.getInstance().setResult(ErrorCodeEnum.UPDATE_FAILED).setData(null);
        }
    }

    @ApiOperation(value = "更新文件信息")
    @PutMapping("/updateSysFileInfo")
    public ReturnInfo updateSysFileInfo(@ApiParam(name = "SysFileInfo", value = "文件对象", required = true) @RequestBody SysFileInfo sysFileInfo) {
        int flag = sysFileInfoService.updateSysFileInfo(sysFileInfo);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "删除文件信息")
    @DeleteMapping("/deleteSysFileInfo/{fileId}")
    @Transactional
    public ReturnInfo deleteSysFileInfo(@ApiParam(name = "fileId", value = "文件ID", required = true) @PathVariable String fileId) {
        //删除视频分段信息
        videoSubService.deleteVideoSubById(fileId);
        int flag = sysFileInfoService.deleteSysFileInfo(fileId);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }
}
