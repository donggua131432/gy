package com.gy.domain.dto.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "WholeMakeup",description = "整装实体")
@Data
public class WholeMakeup implements Serializable {
    private static final long serialVersionUID = -5704863496246198763L;
    @ApiModelProperty("整装ID")
    private String wholeMakeupId;
    @ApiModelProperty("整装原名")
    private String wholeMakeupName;
    @ApiModelProperty("入口名")
    private String inleName;
    @ApiModelProperty("品牌商ID")
    private String brandId;
    @ApiModelProperty("KOL")
    private String kolId;
    @ApiModelProperty("封面底色")
    private String coverBackground;
    @ApiModelProperty("封面标题")
    private String coverTitle;
    @ApiModelProperty("封面色号")
    private String coverTitleColour;
    @ApiModelProperty("整装状态【0:下线 1:上线】")
    private String wholeMakeupStatus;
    @ApiModelProperty("创建时间")
    @JSONField(format = "yy-MM-dd HH:mm:ss")
    private Date createTime;

}