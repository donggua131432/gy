package com.gy.controller;

import com.github.pagehelper.PageInfo;
import com.gy.domain.ResultPage;
import com.gy.domain.ReturnInfo;
import com.gy.domain.dto.sys.FileInfoRel;
import com.gy.domain.dto.sys.Kol;
import com.gy.domain.dto.sys.SysFileInfo;
import com.gy.domain.vo.req.KolQuery;
import com.gy.domain.vo.req.PageCondition;
import com.gy.enums.ErrorCodeEnum;
import com.gy.service.makeup.FileInfoRelService;
import com.gy.service.makeup.KolService;
import com.gy.service.sys.SysFileInfoService;
import com.gy.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @描述: Kol接口
 * @作者: FangLin
 * @创建时间: 2018/12/19 19:50
 * @版本号: V1.0
 */
@RestController
@Api(tags = "引用库接口",description = "Brand-API")
@RequestMapping("/kol")
public class KolController {
    @Autowired
    private KolService kolService;
    @Autowired
    private SysFileInfoService sysFileInfoService;
    @Autowired
    private FileInfoRelService fileInfoRelService;

    @ApiOperation(value = "分页查询Kol")
    @PostMapping("/listKolPage")
    public ReturnInfo<ResultPage<Kol>> listKolPage(@ApiParam(name = "KolQuery", value = "Kol查询对象") @RequestBody KolQuery kolQuery) {
        PageInfo<Kol> pageInfo = kolService.listKolPage(kolQuery);
        ResultPage<Kol> resultPage = ResultPage.getInstance().setPageNum(pageInfo.getPageNum())
                .setPages(pageInfo.getPages())
                .setTotal(pageInfo.getTotal())
                .setList(pageInfo.getList());
        return new ReturnInfo().setResult(ErrorCodeEnum.SUCCESS).setData(resultPage);
    }

    @ApiOperation(value = "添加Kol")
    @PostMapping("/addKol")
    public ReturnInfo addKol(@ApiParam(name = "kolName", value = "kol名称", required = true) @RequestParam String kolName,
                             @ApiParam(name = "fileId",value = "文件Id",required = true)@RequestParam String fileId) {
        String kolId = StringUtil.UUID();
        FileInfoRel fileInfoRel = new FileInfoRel();
        fileInfoRel.setRelId(kolId);
        fileInfoRel.setFileId(fileId);
        fileInfoRel.setFileType("8");
        fileInfoRelService.addFileInfoRel(fileInfoRel);
        int flag = kolService.addKol(kolId,kolName);

        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "更新Kol")
    @PutMapping("/updateKol")
    @Transactional
    public ReturnInfo updateKol(@ApiParam(name = "Kol", value = "Kol对象", required = true) @RequestBody Kol kol) {
        FileInfoRel fileInfoRel = new FileInfoRel();
        fileInfoRel.setFileId(kol.getFileId());
        fileInfoRelService.updateFileInfoRel(fileInfoRel);
        int flag = kolService.updateKol(kol);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "删除Kol")
    @DeleteMapping("/deleteKol/{kolId}/{fileId}")
    @Transactional
    public ReturnInfo deleteKol(@ApiParam(name = "kolId", value = "kolID", required = true) @PathVariable String kolId,
                                @ApiParam(name = "fileId",value = "文件ID",required = true)@PathVariable String fileId) {
        //改变文件状态
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setFileId(fileId);
        sysFileInfo.setFileStatus("1");
        sysFileInfoService.updateSysFileInfo(sysFileInfo);
        //删除文件业务关系表数据
        fileInfoRelService.deleteFileInfoRel(fileId);
        //删除KOL
        int flag = kolService.deleteKol(kolId);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

}
