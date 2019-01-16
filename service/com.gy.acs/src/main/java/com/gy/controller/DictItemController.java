package com.gy.controller;

import com.gy.domain.ReturnInfo;
import com.gy.domain.dto.sys.DictItem;
import com.gy.enums.ErrorCodeEnum;
import com.gy.service.sys.DictItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @描述: 数据字典值接口
 * @作者: FangLin
 * @创建时间: 2018/11/20 15:59
 * @版本号: V1.0
 *  
 */
@RestController
@Api(tags = "数据字典接口",description = "Dict-API")
@RequestMapping("/dictItem")
public class DictItemController {
    @Autowired
    private DictItemService dictItemService;

    @ApiOperation(value = "获取字典值", notes = "获取字典值")
    @GetMapping("/getDictItem/{dictId}")
    public ReturnInfo<List<DictItem>> getDictItem(@ApiParam(name = "dictId",value = "字典ID",required = true)@PathVariable String dictId) {
        List<DictItem> dictItem = dictItemService.getDictItem(dictId);
        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(dictItem);
    }

    @ApiOperation(value = "添加字典值", notes = "添加字典值")
    @PostMapping("/addDictItem")
    public ReturnInfo addDictItem(@ApiParam(name = "dictItem",value = "数据字典值") @RequestBody DictItem dictItem) {
        int flag = dictItemService.addDictItem(dictItem);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "更新字典值", notes = "更新字典值")
    @PutMapping("/updateDictItem")
    public ReturnInfo updateDictItem(@ApiParam(name = "dictItem",value = "数据字典值") @RequestBody DictItem dictItem) {
        int flag = dictItemService.updateDictItem(dictItem);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "删除数据字典值", notes = "删除数据字典值")
    @DeleteMapping("/deleteDictItem/{itemId}")
    public ReturnInfo deleteDictItem(@ApiParam(name = "dictItemId", value = "数据字典值ID", required = true) @PathVariable String itemId) {
        int flag = dictItemService.deleteDictItem(itemId);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }
}
