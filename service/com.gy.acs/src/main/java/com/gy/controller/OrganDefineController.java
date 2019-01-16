package com.gy.controller;

import com.github.pagehelper.PageInfo;
import com.gy.domain.ResultPage;
import com.gy.domain.ReturnInfo;
import com.gy.domain.dto.makeup.OrganDefine;
import com.gy.domain.dto.makeup.OrganDefineArray;
import com.gy.domain.vo.req.PageCondition;
import com.gy.domain.vo.res.OrganDefineVo;
import com.gy.enums.ErrorCodeEnum;
import com.gy.service.makeup.OrganDefineArrayService;
import com.gy.service.makeup.OrganDefineService;
import com.gy.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @描述: 重新定义器官和重新定义器官排列组合接口
 * @作者: FangLin
 * @创建时间: 2018/12/15 14:55
 * @版本号: V1.0
 *  
 */

@RestController
@Api(tags = {"重新定义器官名接口"},description = "OrganDefineController-API")
@RequestMapping("/organDefine")
public class OrganDefineController {

    @Autowired
    private OrganDefineService organDefineService;

    @Autowired
    private OrganDefineArrayService organDefineArrayService;


    @ApiOperation(value = "查询重新定义器官分页信息")
    @PostMapping("/listOrganDefinePage")
    public ReturnInfo<ResultPage<OrganDefineVo>> listOrganDefinePage(
            @ApiParam(name = "organId",value = "器官ID") @RequestParam String organId,
            @ApiParam(name = "organName",value = "器官名称") @RequestParam String organName,
            @ApiParam(name = "page",value = "分页信息",required = true) @RequestBody PageCondition page) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("organId",organId);
        paramMap.put("organName",organName);
        PageInfo<OrganDefineVo> pageInfo = organDefineService.listOrganDefinePage(page,paramMap);
        ResultPage<OrganDefineVo> resultPage = ResultPage.getInstance().setPageNum(pageInfo.getPageNum())
                .setPages(pageInfo.getPages())
                .setTotal(pageInfo.getTotal())
                .setList(pageInfo.getList());
        return new ReturnInfo().setResult(ErrorCodeEnum.SUCCESS).setData(resultPage);
    }

    @ApiOperation(value = "添加重新定义器官名")
    @PostMapping("/addOrganDefine")
    @Transactional
    public ReturnInfo addOrganDefine(
            @ApiParam(name = "OrganDefine", value = "重新定义器官名", required = true) @RequestBody OrganDefine organDefine) {
        String organDefineId = StringUtil.UUID();
        organDefine.setOrganDefineId(organDefineId);
        organDefineService.addOrganDefine(organDefine);

        List<OrganDefineArray> odaList = new ArrayList<>();
        for (String str: organDefine.getOrganDefineList()) {
            String[] strArr = str.split(",");
            OrganDefineArray oda = new OrganDefineArray();
            oda.setOrganDefineId(organDefineId);
            oda.setFeatureTypeId(strArr[0]);
            oda.setFeatureId(strArr[1]);
            odaList.add(oda);
        }

        //批量添加排列组合数据
        if(odaList.size()>0){
            organDefineArrayService.addOrganDefineArrayBatch(odaList);
        }
        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(null);
    }

    @ApiOperation(value = "修改重新定义器官")
    @PutMapping("/updateOrganDefine")
    @Transactional
    public ReturnInfo updateOrganDefine(
            @ApiParam(name = "OrganDefine", value = "重新定义器官对象", required = true) @RequestBody OrganDefine organDefine){
        //添加重新定义器官名
        organDefineService.updateOrganDefine(organDefine);

        //获取请求排列组合集合
        List<String> reqOdList = organDefine.getOrganDefineList();
        //临时集合信息存放两个集合的交集
        List<String> tempList = new ArrayList<>();
        tempList.addAll(reqOdList);
        //获取是数据库排列组合集合
        List<String> resultOdList = organDefineArrayService.listOrganDefineArrayById(organDefine.getOrganDefineId());
        //取两个集合的交集
        tempList.retainAll(resultOdList);

        //批量添加排列组合数据
        reqOdList.removeAll(tempList);
        List<OrganDefineArray> odaList = new ArrayList<>();
        reqOdList.stream().forEach(str->{
            String[] strArr = str.split(",");
            OrganDefineArray oda = new OrganDefineArray();
            oda.setOrganDefineId(organDefine.getOrganDefineId());
            oda.setFeatureTypeId(strArr[0]);
            oda.setFeatureId(strArr[1]);
            odaList.add(oda);
        });

        if(odaList.size()>0){
            organDefineArrayService.addOrganDefineArrayBatch(odaList);
        }

        //批量删除排列组合数据
        resultOdList.removeAll(tempList);
        if(resultOdList.size()>0){
            organDefineArrayService.deleteOrganDefineArrayBatch(resultOdList);
        }

        int flag = organDefineService.updateOrganDefine(organDefine);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "删除重新定义器官")
    @DeleteMapping("/deleteOrganDefine/{organDefineId}")
    public ReturnInfo deleteOrganDefine(
            @ApiParam(name = "organDefineId", value = "重新定义器官对象ID", required = true) @PathVariable String organDefineId) {
        int flag = organDefineService.deleteOrganDefine(organDefineId);
        ErrorCodeEnum ee = ErrorCodeEnum.SUCCESS;
        if(flag>0){
            int odaDelFlag = organDefineArrayService.deleteOrganDefineArray(organDefineId);
            if(odaDelFlag>0){
                ee = ErrorCodeEnum.FAILED;
            }
        }
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

}
