package com.gy.controller;

import com.github.pagehelper.PageInfo;
import com.gy.domain.ResultPage;
import com.gy.domain.ReturnInfo;
import com.gy.domain.dto.sys.Dict;
import com.gy.domain.vo.req.DictQuery;
import com.gy.enums.ErrorCodeEnum;
import com.gy.service.sys.DictItemService;
import com.gy.service.sys.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @描述: 数据字典接口
 * @作者: FangLin
 * @创建时间: 2018/11/20 12:45
 * @版本号: V1.0
 *  
 */
@RestController
@Api(tags = {"数据字典接口"},description = "Dict-API")
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;
    @Autowired
    private DictItemService dictItemService;

    @ApiOperation(value = "获取分页数据字典列表",notes = "获取全部数据字典列")
    @PostMapping ("/listAllDictPage")
    public ReturnInfo<ResultPage<Dict>> listAllDictPage(@ApiParam("字典查询参数") @RequestBody DictQuery dictQuery) {
        PageInfo<Dict> pageInfo = dictService.listDictPage(dictQuery);
        ResultPage<Dict> resultPage = ResultPage.getInstance().setPageNum(pageInfo.getPageNum())
                .setPages(pageInfo.getPages())
                .setTotal(pageInfo.getTotal())
                .setList(pageInfo.getList());
        return new ReturnInfo<Dict>().setResult(ErrorCodeEnum.SUCCESS).setData(resultPage);
    }

    @ApiOperation(value = "添加数据字典", notes = "添加数据字典")
    @PostMapping("/addDict")
    public ReturnInfo addDict(@ApiParam(name = "dict", value = "字典对象", required = true)@RequestBody Dict dict) {
        int flag = dictService.addDict(dict);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "通过dictId删除数据字典", notes = "通过dictId删除数据字典")
    @DeleteMapping("/deleteDict/{dictId}")
    @Transactional
    public ReturnInfo deleteDict(@ApiParam(name = "dictId", value = "字典ID", required = true) @PathVariable String dictId) {
        int flag = dictService.deleteDict(dictId);
        dictItemService.deleteDictItemByDictId(dictId);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

}
