package com.gy.domain.vo.res;

import com.alibaba.fastjson.annotation.JSONField;
import com.gy.domain.dto.sys.FileInfoRel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @描述: 整装库返回实体
 * @作者: FangLin
 * @创建时间: 2018/12/28 16:35
 * @版本号: V1.0
 */
@ApiModel(value = "WholeMakeupVo",description = "整装库返回实体")
@Data
public class WholeMakeupVo {

    @ApiModelProperty("整装ID")
    private String wholeMakeupId;
    @ApiModelProperty("整装原名")
    private String wholeMakeupName;
    @ApiModelProperty("入口名")
    private String inleName;
    @ApiModelProperty("品牌商ID")
    private String brandId;
    @ApiModelProperty("品牌商名称")
    private String brandName;
    @ApiModelProperty("KOL")
    private String kolId;
    @ApiModelProperty("KOL名称")
    private  String kolName;
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

    @ApiModelProperty("入口图和模特封页图")
    private List<FileInfoRel> fileInfoRelList;
}
