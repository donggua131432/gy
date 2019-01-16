package com.gy.domain.dto.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@ApiModel(value = "DictItem",description = "数据字典值")
@Data
public class DictItem implements Serializable {

    private static final long serialVersionUID = -8607542021970747405L;
    @ApiModelProperty("字典值ID")
    private String itemId;
    @ApiModelProperty("字典ID")
    private String dictId;
    @ApiModelProperty("字典值名称")
    private String itemName;
    @ApiModelProperty("字典值")
    private String itemValue;
    @ApiModelProperty("字典值排序")
    private Integer itemSort;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("字典值添加时间")
    private Date createTime;


}