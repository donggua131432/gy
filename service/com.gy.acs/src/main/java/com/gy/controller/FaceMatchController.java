package com.gy.controller;

import com.gy.domain.ReturnInfo;
import com.gy.domain.dto.makeup.FaceMatch;
import com.gy.domain.vo.res.FaceMatchVo;
import com.gy.enums.ErrorCodeEnum;
import com.gy.service.makeup.FaceMatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @描述: 五官分析匹配接口
 * @作者: FangLin
 * @创建时间: 2018/12/22 11:45
 * @版本号: V1.0
 */
@RestController
@Api(tags={"整装库接口"}, description = "WholeMakeup-API")
@RequestMapping("/faceMatch")
public class FaceMatchController {
    @Autowired
    private FaceMatchService faceMatchService;

    @ApiOperation(value = "查询五官分析匹配信息")
    @GetMapping("/listFaceMatch")
    public ReturnInfo listFaceMatch() {
        List<FaceMatchVo> faceMatchList = faceMatchService.listFaceMatch();
        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(faceMatchList);
    }

    @ApiOperation(value = "添加五官分析信息")
    @PostMapping("/addFaceMatch")
    public ReturnInfo addFaceMatch(@ApiParam(name = "FaceMatch", value = "五官分析匹配实体", required = true) @RequestBody FaceMatch faceMatch) {
        int flag = faceMatchService.addFaceMatch(faceMatch);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "更新五官分析匹配")
    @PutMapping("/updateFaceMatch")
    public ReturnInfo updateFaceMatch(@ApiParam(name = "FaceMatch", value = "五官分析匹配实体", required = true) @RequestBody FaceMatch faceMatch) {
        int flag = faceMatchService.updateFaceMatch(faceMatch);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "删除五官分析匹配")
    @DeleteMapping("/deleteFaceMatch/{faceMatchId}")
    public ReturnInfo deleteFaceMatch(@ApiParam(name = "faceMatchId", value = "五官分析匹配ID", required = true) @PathVariable("faceMatchId") String faceMatchId) {
        int flag = faceMatchService.deleteFaceMatch(faceMatchId);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }
}
