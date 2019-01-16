package com.gy.controller;

import com.gy.domain.ReturnInfo;
import com.gy.domain.dto.makeup.Organ;
import com.gy.enums.ErrorCodeEnum;
import com.gy.service.makeup.OrganService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @描述: 五官分析
 * @作者: FangLin
 * @创建时间: 2018/12/13 14:45
 * @版本号: V1.0
 *  
 */
@RestController
@Api(tags = "五官分析接口",description = "makeup-API")
@RequestMapping("/organ")
public class OrganController {
    @Autowired
    private OrganService organService;

    @ApiOperation(value = "器官信息分页查询", notes = "器官信息分页查询")
    @PostMapping("/listOrgan")
    public ReturnInfo listOrgan() {
        List<Organ> organList = organService.listOrgan();
        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(organList);
    }

    @ApiOperation(value = "添加器官信息")
    @PostMapping("/addOrgan")
    public ReturnInfo addOrgan(@ApiParam(name = "organ", value = "器官信息", required = true) @RequestBody Organ organ) {
        int flag = organService.addOrgan(organ);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "修改器官信息")
    @PutMapping("/updateOrgan")
    public ReturnInfo updateOrgan(@ApiParam(name = "organ", value = "器官信息", required = true) @RequestBody Organ organ) {
        int flag = organService.updateOrgan(organ);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "删除器官信息")
    @DeleteMapping("/deleteOrgan/{organId}")
    public ReturnInfo deleteOrgan(@ApiParam(name = "organId", value = "器官ID", required = true) @PathVariable String organId) {
        int flag = organService.deleteOrgan(organId);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

}
