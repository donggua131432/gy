package com.gy.controller;

import com.github.pagehelper.PageInfo;
import com.gy.domain.ResultPage;
import com.gy.domain.ReturnInfo;
import com.gy.domain.dto.makeup.FeatureType;
import com.gy.domain.vo.req.makeup.FeatureTypeQuery;
import com.gy.enums.ErrorCodeEnum;
import com.gy.service.makeup.FeatureTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @描述: 五官分析
 * @作者: FangLin
 * @创建时间: 2018/12/13 16:15
 * @版本号: V1.0
 *  
 */

@RestController
@Api(tags = "五官分析接口",description = "makeup-API")
@RequestMapping("/featureType")
public class FeatureTypeController {
    @Autowired
    private FeatureTypeService featureTypeService;

    @ApiOperation(value = "特征类型信息分页查询")
    @PostMapping("/listFeatureTypePage")
    public ReturnInfo<ResultPage<FeatureType>> listFeatureTypePage(@ApiParam(name = "FeatureTypeQuery",
            value = "器官特征类型查询实体", required = true) @RequestBody FeatureTypeQuery featureTypeQuery) {

        PageInfo<FeatureType> pageInfo = featureTypeService.selectListFeatureTypePage(featureTypeQuery);
        ResultPage<FeatureType> resultPage = ResultPage.getInstance().setPageNum(pageInfo.getPageNum())
                .setPages(pageInfo.getPages())
                .setTotal(pageInfo.getTotal())
                .setList(pageInfo.getList());
        return new ReturnInfo<FeatureType>().setResult(ErrorCodeEnum.SUCCESS).setData(resultPage);
    }
    @ApiOperation(value = "添加特征类型信息")
    @PostMapping("/addFeatureType")
    public ReturnInfo addFeatureType(@ApiParam(name = "FeatureType", value = "五官类型对象", required = true) @RequestBody FeatureType featureType) {
        int flag = featureTypeService.addFeatureType(featureType);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "修改特征类型信息")
    @PutMapping("/updateFeatureType")
    public ReturnInfo updateFeatureType(@ApiParam(name = "FeatureType", value = "五官类型对象", required = true) @RequestBody FeatureType featureType) {
        int flag = featureTypeService.updateFeatureType(featureType);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "删除特征类型信息")
    @DeleteMapping("/deleteFeatureType/{featureTypeId}")
    public ReturnInfo deleteFeatureType(@ApiParam(name = "featureTypeId", value = "五官类型ID", required = true) @PathVariable String featureTypeId) {
        int flag = featureTypeService.deleteFeatureType(featureTypeId);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

}
