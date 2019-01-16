package com.gy.controller;

import com.github.pagehelper.PageInfo;
import com.gy.domain.ResultPage;
import com.gy.domain.ReturnInfo;
import com.gy.domain.dto.sys.Brand;
import com.gy.domain.dto.sys.FileInfoRel;
import com.gy.domain.dto.sys.SysFileInfo;
import com.gy.domain.vo.req.BrandQuery;
import com.gy.domain.vo.req.PageCondition;
import com.gy.enums.ErrorCodeEnum;
import com.gy.service.makeup.BrandService;
import com.gy.service.makeup.FileInfoRelService;
import com.gy.service.sys.SysFileInfoService;
import com.gy.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


/**
 * @描述: 品牌商接口
 * @作者: FangLin
 * @创建时间: 2018/12/19 18:15
 * @版本号: V1.0
 */
@RestController
@Api(tags = "引用库接口",description = "Brand-API")
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;
    @Autowired
    private SysFileInfoService sysFileInfoService;
    @Autowired
    private FileInfoRelService fileInfoRelService;

    @ApiOperation(value = "分页查询品牌商")
    @PostMapping("/listBrandPage")
    public ReturnInfo<ResultPage<Brand>> listBrandPage(@ApiParam(name = "BrandQuery", value = "品牌商查询对象") @RequestBody BrandQuery brandQuery) {
        PageInfo<Brand> pageInfo = brandService.listBrandPage(brandQuery);
        ResultPage<Brand> resultPage = ResultPage.getInstance().setPageNum(pageInfo.getPageNum())
                .setPages(pageInfo.getPages())
                .setTotal(pageInfo.getTotal())
                .setList(pageInfo.getList());
        return new ReturnInfo().setResult(ErrorCodeEnum.SUCCESS).setData(resultPage);
    }

    @ApiOperation("添加品牌商")
    @PostMapping("/addBrand")
    @Transactional
    public ReturnInfo addBrand(@ApiParam(name = "brandName", value = "品牌商名称", required = true) @RequestParam String brandName,
                               @ApiParam(name = "fileId",value = "文件ID",required = true) @RequestParam String  fileId) {
        String brandId = StringUtil.UUID();
        //添加文件业务关系信息
        FileInfoRel fileInfoRel = new FileInfoRel();
        fileInfoRel.setRelId(brandId);
        fileInfoRel.setFileId(fileId);
        fileInfoRel.setFileType("7");
        fileInfoRelService.addFileInfoRel(fileInfoRel);
        System.out.println("brandID:"+brandId);
        int flag = brandService.addBrand(brandId,brandName);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "更新品牌商")
    @PutMapping("/updateBrand")
    @Transactional
    public ReturnInfo updateBrand(@ApiParam(name = "Brand", value = "品牌商对象", required = true) @RequestBody Brand brand) {
        //更新文件业务关系信息
        FileInfoRel fileInfoRel = new FileInfoRel();
        fileInfoRel.setRelId(brand.getBrandId());
        fileInfoRel.setFileId(brand.getFileId());
        fileInfoRelService.updateFileInfoRel(fileInfoRel);
        int flag = brandService.updateBrand(brand);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }

    @ApiOperation(value = "删除品牌商")
    @DeleteMapping("/deleteBrand/{brandId}/{fileId}")
    @Transactional
    public ReturnInfo deleteBrand(@ApiParam(name = "brandId", value = "品牌商ID", required = true) @PathVariable String brandId,
                                  @ApiParam(name = "fileId",value = "文件ID",required = true)@PathVariable String fileId) {
        //改变文件状态
        SysFileInfo sysFileInfo = new SysFileInfo();
        sysFileInfo.setFileId(fileId);
        sysFileInfo.setFileStatus("1");
        sysFileInfoService.updateSysFileInfo(sysFileInfo);
        //删除文件业务关系表的信息
        fileInfoRelService.deleteFileInfoRel(fileId);
        //删除品牌商
        int flag = brandService.deleteBrand(brandId);
        ErrorCodeEnum ee = flag > 0 ? ErrorCodeEnum.SUCCESS:ErrorCodeEnum.FAILED;
        return ReturnInfo.getInstance().setResult(ee).setData(null);
    }
}
