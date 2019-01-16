package com.gy.controller;

import com.github.pagehelper.PageInfo;
import com.gy.domain.ResultPage;
import com.gy.domain.ReturnInfo;
import com.gy.domain.dto.makeup.OrganFeature;
import com.gy.domain.vo.req.makeup.OrganFeatureQuery;
import com.gy.domain.vo.res.OrganFeatureVo;
import com.gy.enums.ErrorCodeEnum;
import com.gy.service.makeup.OrganFeatureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @描述: 五官分析接口
 * @描述: 特征类型和器官特征值接口
 * @作者: FangLin
 * @创建时间: 2018/12/18 14:55
 * @版本号: V1.0
 *  
 */
@RestController
@Api(tags = "五官分析接口",description = "makeup-API")
@RequestMapping("/organFeature")
public class OrganFeatureController {

    @Autowired
    private OrganFeatureService organFeatureService;

    @ApiOperation(value = "器官特征值信息分页查询")
    @PostMapping("/listOrganPage")
    public ReturnInfo<ResultPage<OrganFeatureVo>> listOrganComprehensive(@ApiParam(name = "OrganFeatureVo",
            value = "器官特征值查询实体", required = true) @RequestBody OrganFeatureQuery organFeatureQuery) {
        PageInfo<OrganFeatureVo> pageInfo = organFeatureService.listOrganFeaturePage(organFeatureQuery);
        ResultPage<OrganFeatureVo> resultPage = ResultPage.getInstance().setPageNum(pageInfo.getPageNum())
                .setPages(pageInfo.getPages())
                .setTotal(pageInfo.getTotal())
                .setList(pageInfo.getList());
        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(resultPage);
    }

    @ApiOperation(value = "添加器官特征值信息")
    @PostMapping("/addOrganFeature")
    public ReturnInfo addOrganFeature(@ApiParam(name = "OrganFeature", value = "器官特征值对象", required = true) @RequestBody OrganFeature organFeature) {
        int flag = organFeatureService.addOrganFeature(organFeature);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "修改器官特征值信息")
    @PutMapping("/updateOrganFeature")
    public ReturnInfo updateOrganFeature(@ApiParam(name = "OrganFeature", value = "器官特征值对象", required = true) @RequestBody OrganFeature organFeature) {
        int flag = organFeatureService.updateOrganFeature(organFeature);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "删除器官特征值信息")
    @DeleteMapping("/deleteOrganFeature/{organFeatureId}")
    public ReturnInfo deleteOrganFeature(@ApiParam(name = "organFeatureId", value = "器官特征值ID", required = true) @PathVariable String organFeatureId) {
        int flag = organFeatureService.deleteOrganFeature(organFeatureId);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }
}
