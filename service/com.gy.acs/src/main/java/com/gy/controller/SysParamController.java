package com.gy.controller;

import com.gy.domain.ReturnInfo;
import com.gy.domain.dto.sys.SysParam;
import com.gy.enums.ErrorCodeEnum;
import com.gy.service.sys.SysParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @描述: 系统参数
 * @作者: DuKai
 * @创建时间: 2018/12/3 18:08
 * @版本号: V1.0
 */

@RestController
@Api(tags={"系统管理接口"}, description = "Sys-API")
@RequestMapping("/sysParam")
public class SysParamController {
    private final static Logger logger = LoggerFactory.getLogger(SysParamController.class);

    @Autowired
    private SysParamService sysParamService;

    @ApiOperation(value = "获取系统参数信息", notes="获取系统参数信息")
    @GetMapping("/getSysParam/{paramId}")
    public ReturnInfo<SysParam> getSysParam(@ApiParam(value="系统参数ID",required=true) @PathVariable String paramId) {
        SysParam sysParam = sysParamService.getSysParam(paramId);
        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(sysParam);
    }

}
