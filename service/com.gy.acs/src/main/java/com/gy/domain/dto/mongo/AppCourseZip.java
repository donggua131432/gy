package com.gy.domain.dto.mongo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @描述: 试装包信息
 * @作者: DuKai
 * @创建时间: 2018/12/19 15:47
 * @版本号: V1.0
 */
@ApiModel(value = "AppCourseZip",description = "试装包信息")
@Data
public class AppCourseZip implements Serializable {
    private static final long serialVersionUID = 2572260506942179122L;

    @ApiModelProperty("试装包Id")
    private String id;
    @ApiModelProperty("试装包名称")
    private String name;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("试装包地址")
    private String downloadurl;
    @ApiModelProperty("创建时间")
    private String createDate;
    @ApiModelProperty("创建用户")
    private String createBy;
    @ApiModelProperty("是否禁用：【0.关闭 1.是开】")
    private int enabled;
    @ApiModelProperty("素材Id")
    private int materialid;
    @ApiModelProperty("素材参数里的素材名称")
    private String styleName;

}
