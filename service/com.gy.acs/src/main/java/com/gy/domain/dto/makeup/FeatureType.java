package com.gy.domain.dto.makeup;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value="FeatureType", description = "特征类型")
@Data
public class FeatureType implements Serializable {
    private static final long serialVersionUID = -8870344880595668366L;

    @ApiModelProperty("特征类型ID")
    private String featureTypeId;
    @ApiModelProperty("器官ID")
    private String organId;
    @ApiModelProperty("特征类型名称")
    private String featureTypeName;
    @ApiModelProperty("优先级")
    private Integer priorityLvl;
    @ApiModelProperty("创建时间")
    @JsonIgnore
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}