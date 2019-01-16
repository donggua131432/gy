package com.gy.domain.dto.makeup;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "Article",description = "文章综合接收实体")
@Data
public class Article implements Serializable {
    private static final long serialVersionUID = 8170615963941990582L;
    //文章信息
    @ApiModelProperty("文章ID")
    private String articleId;
    @ApiModelProperty("整装ID")
    private String wholeMakeupId;
    @ApiModelProperty("外链文字")
    private String linkText;
    @ApiModelProperty("链接地址")
    private String linkUrl;
    @ApiModelProperty("文章标题")
    private String articleTitle;
    @JsonIgnore
    @ApiModelProperty("创建时间")
    @JSONField(format = "yy-MM-dd HH:mm:ss")
    private Date createTime;
    //封页图信息
    @ApiModelProperty("封页图ID")
    private String imageId;
    @ApiModelProperty("封页图地址")
    private String imageAddress;
    @ApiModelProperty("封页图排序")
    private int sort;
    //文章内容信息
    private List<ArticleContent> articleContentList;

}