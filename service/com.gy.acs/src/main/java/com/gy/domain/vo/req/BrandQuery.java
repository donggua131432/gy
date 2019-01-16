package com.gy.domain.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @描述: 品牌商查询实体
 * @作者: FangLin
 * @创建时间: 2018/12/27 16:37
 * @版本号: V1.0
 */
@ApiModel(value = "BrandQuery",description = "品牌商查询实体")
@Data
public class BrandQuery extends PageCondition implements Serializable {

    private static final long serialVersionUID = 8643163493408807446L;

    @ApiModelProperty("品牌商名")
    private String brandName;
}
