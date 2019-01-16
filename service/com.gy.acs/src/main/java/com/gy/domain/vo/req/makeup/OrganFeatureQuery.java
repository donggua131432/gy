package com.gy.domain.vo.req.makeup;

import com.gy.domain.vo.req.PageCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @描述: 器官特征查询实体
 * @作者: FangLin
 * @创建时间: 2018/12/13 18:08
 * @版本号: V1.0
 */
@ApiModel(value = "OrganFeatureQuery",description = "器官特征查询实体")
@Data
public class OrganFeatureQuery extends PageCondition implements Serializable {
    private static final long serialVersionUID = -8743425276426955835L;

    @ApiModelProperty("器官特征类型名称")
    private String featureName;
    @ApiModelProperty("器官ID")
    private String organId;
    @ApiModelProperty("器官特征类型ID")
    private String featureTypeId;

}
