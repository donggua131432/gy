package com.gy.domain.dto.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@ApiModel(value = "Dict",description = "数据字典")
@Data
public class Dict implements Serializable {
    private static final long serialVersionUID = 2137058948817290574L;

    @ApiModelProperty("字典ID")
    private String dictId;
    @ApiModelProperty("字典编码")
    private String dictCode;
    @ApiModelProperty("字典名称")
    private String dictName;
    @ApiModelProperty("创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


}