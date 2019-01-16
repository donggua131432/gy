package com.gy.service.makeup;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gy.domain.dto.sys.Brand;
import com.gy.domain.vo.req.BrandQuery;
import com.gy.domain.vo.req.PageCondition;
import com.gy.mapper.sys.BrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @描述: 品牌服务层
 * @作者: FangLin
 * @创建时间: 2018/12/19 16:49
 * @版本号: V1.0
 *  
 */
@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;

    public PageInfo<Brand> listBrandPage(BrandQuery brandQuery) {
        PageHelper.startPage(brandQuery.getPage(),brandQuery.getSize());
        List<Brand> brandList = brandMapper.selectBrandPage(brandQuery);
        return new PageInfo<>(brandList);
    }

    public int addBrand(String brandId,String  brandName) {
        return brandMapper.insertBrand(brandId,brandName);
    }

    public int updateBrand(Brand brand) {
        return brandMapper.updateBrandById(brand);
    }

    public int deleteBrand(String brandId) {
        return brandMapper.deleteBrandById(brandId);
    }

}
