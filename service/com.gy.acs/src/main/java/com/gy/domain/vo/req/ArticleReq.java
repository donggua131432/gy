package com.gy.domain.vo.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 文章实体
 * @作者: FangLin
 * @创建时间: 2018/12/25 15:14
 * @版本号: V1.0
 */
@ApiModel(value = "Article",description = "文章实体")
@Data
public class ArticleReq implements Serializable {
    private static final long serialVersionUID = 8866245710507352903L;
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
}
