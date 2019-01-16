package com.gy.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @描述: 文件信息
 * @作者: DuKai
 * @创建时间: 2018/12/17 11:33
 * @版本号: V1.0
 */
@ApiModel(value = "FileInfo",description = "文件信息")
@Data
public class FileInfo implements Serializable {
    private static final long serialVersionUID = 8777358872251707083L;

    @ApiModelProperty("文件名称")
    private String fileName;
    @ApiModelProperty("文件后缀")
    private String fileSuffix;
    @ApiModelProperty("文件大小")
    private int fileSize;
    @ApiModelProperty("播放时长")
    private int playTime;
    @ApiModelProperty("文件地址")
    private String filePath;

}
