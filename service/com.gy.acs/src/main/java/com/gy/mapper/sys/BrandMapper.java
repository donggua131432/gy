package com.gy.mapper.sys;

import com.gy.domain.dto.sys.Brand;
import com.gy.domain.vo.req.BrandQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandMapper {
    int deleteBrandById(String brandId);

    int insertBrand(@Param("brandId") String brandId,@Param("brandName") String  brandName);

    List<Brand> selectBrandPage(BrandQuery brandQuery);

    int updateBrandById(Brand record);

}