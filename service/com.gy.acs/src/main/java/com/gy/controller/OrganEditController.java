package com.gy.controller;

import com.github.pagehelper.PageInfo;
import com.gy.domain.ResultPage;
import com.gy.domain.ReturnInfo;
import com.gy.domain.dto.makeup.OrganFeature;
import com.gy.domain.vo.req.makeup.OrganFeatureQuery;
import com.gy.domain.vo.res.OrganFeatureVo;
import com.gy.enums.ErrorCodeEnum;
import com.gy.service.makeup.FeatureTypeService;
import com.gy.service.makeup.OrganFeatureService;
import com.gy.service.makeup.OrganService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @描述: 五官编辑接口
 * @作者: FangLin
 * @创建时间: 2018/12/26 17:13
 * @版本号: V1.0
 */
@RestController
@Api(tags = "五官分析接口",description = "makeup-API")
@RequestMapping("/organEdit")
public class OrganEditController {
    @Autowired
    private OrganService organService;
    @Autowired
    private FeatureTypeService featureTypeService;
    @Autowired
    private OrganFeatureService organFeatureService;
    @ApiOperation(value = "五官编辑的查询")
    @PostMapping("/listOrganEditPage")
    public ReturnInfo<ResultPage<OrganFeatureVo>> listOrganFeaturePage(@ApiParam(name = "五官编辑查询对象", value = "OrganFeatureQuery") @RequestBody OrganFeatureQuery organFeatureQuery) {
        PageInfo<OrganFeatureVo> pageInfo = organFeatureService.listOrganFeaturePage(organFeatureQuery);
        ResultPage<OrganFeatureVo> resultPage = ResultPage.getInstance().setPageNum(pageInfo.getPageNum())
                .setPages(pageInfo.getPages())
                .setTotal(pageInfo.getTotal())
                .setList(pageInfo.getList());
        return new ReturnInfo().setResult(ErrorCodeEnum.SUCCESS).setData(resultPage);
    }

    @ApiOperation(value = "五官编辑的添加")
    @PostMapping("/addOrganEdit")
    public ReturnInfo addOrganEdit(@ApiParam(name = "OrganFeatureVo", value = "特征值对象", required = true) @RequestBody OrganFeature organFeature) {
        int flag = organFeatureService.addOrganFeature(organFeature);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "五官编辑的更新")
    @PutMapping("/updateOrganEdit")
    public ReturnInfo updateOrganEdit(@ApiParam(name = "OrganFeatureVo", value = "特征值对象", required = true) @RequestBody OrganFeature organFeature) {
        int flag = organFeatureService.updateOrganFeature(organFeature);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "五官编辑的删除")
    @DeleteMapping("/deleteOrganFeature/{organFeatureId}")
    public ReturnInfo deleteOrganFeature(@ApiParam(name = "organFeatureId", value = "特征值ID", required = true) @PathVariable(value = "organFeatureId") String organFeatureId) {
        int flag = organFeatureService.deleteOrganFeature(organFeatureId);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }
}
