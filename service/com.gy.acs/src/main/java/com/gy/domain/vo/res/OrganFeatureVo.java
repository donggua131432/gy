package com.gy.domain.vo.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  * @描述: ${DESCRIPTION}
 *  * @作者: FangLin
 *  * @创建时间: 2018/12/18 17:04
 *  * @版本号: V1.0
 *  
 */
@ApiModel(value = "OrganFeatureVo",description = "器官特征实体")
@Data
public class OrganFeatureVo implements Serializable {

    private static final long serialVersionUID = 2221182859998764516L;

    @ApiModelProperty("特征值ID")
    private String featureId;
    @ApiModelProperty("器官Id")
    private String organId;
    @ApiModelProperty("器官名称")
    private String organName;
    @ApiModelProperty("特征类型ID")
    private String featureTypeId;
    @ApiModelProperty("特征类型名称")
    private String featureTypeName;
    @ApiModelProperty("特征类型编码")
    private String featureCode;
    @ApiModelProperty("特征名称")
    private String featureName;
    @ApiModelProperty("器官特征描述")
    private String featureDesc;
    @ApiModelProperty("特征文案")
    private String featureScript;
}
