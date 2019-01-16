package com.gy.domain.dto.makeup;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 器官特征实体
 * @作者: FangLin
 * @创建时间: 2018/12/19 11:18
 * @版本号: V1.0
 *  
 */
@ApiModel(value = "OrganFeature",description = "器官特征实体")
@Data
public class OrganFeature implements Serializable {
    private static final long serialVersionUID = 3393856543841350599L;
    @ApiModelProperty("器官特征ID")
    private String featureId;
    @ApiModelProperty("特征类型ID")
    private String featureTypeId;
    @ApiModelProperty("器官特征编码")
    private String featureCode;
    @ApiModelProperty("器官特征名称")
    private String featureName;
    @ApiModelProperty("器官特征描述")
    private String featureDesc;
    @ApiModelProperty("器官特征文案")
    private String featureScript;
    @ApiModelProperty("创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}