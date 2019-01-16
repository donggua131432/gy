package com.gy.domain.vo.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @描述: 重新定义器官名排列组合详情实体
 * @作者: DuKai
 * @创建时间: 2018/12/19 18:55
 * @版本号: V1.0
 */
@ApiModel(value = "OrganDefineArrayVo",description = "重新定义器官名排列组合详情实体")
@Data
public class OrganDefineArrayVo implements Serializable {
    private static final long serialVersionUID = 3401278992049990301L;

    @ApiModelProperty("排列组合详情ID")
    private String sodaId;
    @ApiModelProperty("特征类型ID")
    private String featureTypeId;
    @ApiModelProperty("特征类型名称")
    private String featureTypeName;
    @ApiModelProperty("器官特征值ID")
    private String featureId;
    @ApiModelProperty("器官特征名称")
    private String featureName;


}
