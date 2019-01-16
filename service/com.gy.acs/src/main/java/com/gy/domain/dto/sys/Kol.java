package com.gy.domain.dto.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@ApiModel(value = "Kol",description = "KOL")
@Data
public class Kol implements Serializable {

    private static final long serialVersionUID = 3615436817123619201L;
    @ApiModelProperty("KOLId")
    private String kolId;
    @ApiModelProperty("KOL名称")
    private String kolName;
    @ApiModelProperty("创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty("文件地址")
    private String filePath;
    @ApiModelProperty("文件ID")
    private String fileId;

}