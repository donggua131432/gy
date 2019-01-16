package com.gy.domain.dto.makeup;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "makeup",description = "器官实体")
@Data
public class Organ implements Serializable {
    private static final long serialVersionUID = -3244072846855495239L;
    @ApiModelProperty("器官ID")
    private String organId;
    @ApiModelProperty("器官名称")
    private String organName;
    @ApiModelProperty("描述")
    private String organDesc;
    @ApiModelProperty("创建时间")
    @JsonIgnore
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}