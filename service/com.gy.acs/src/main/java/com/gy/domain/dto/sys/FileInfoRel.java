package com.gy.domain.dto.sys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "FileInfoRel")
@Data
public class FileInfoRel implements Serializable {
    private static final long serialVersionUID = 5877651809371274750L;

    @ApiModelProperty("关联ID")
    private String relId;
    @ApiModelProperty("文件ID")
    private String fileId;
    @ApiModelProperty("文件类型")
    private String fileType;
    @ApiModelProperty("排序")
    private Integer fileOrder;
}