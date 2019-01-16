package com.gy.domain.dto.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "SysFileInfo",description = "文件信息实体")
@Data
public class SysFileInfo implements Serializable {
    private static final long serialVersionUID = 3843189693382945557L;

    @ApiModelProperty("文件ID")
    private String fileId;
    @ApiModelProperty("文件名称")
    private String fileName;
    @ApiModelProperty("文件后缀")
    private String fileSuffix;
    @ApiModelProperty("文件大小")
    private BigDecimal fileSize;
    @ApiModelProperty("文件播放时长")
    private Integer playTime;
    @ApiModelProperty("文件地址")
    private String filePath;
    @ApiModelProperty("文件状态")
    private String fileStatus;
    @ApiModelProperty("创建时间")
    @JSONField(format = "yy-MM-dd HH:mm:ss")
    private Date createTime;

}