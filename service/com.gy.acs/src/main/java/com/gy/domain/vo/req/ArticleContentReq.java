package com.gy.domain.vo.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 文章内容实体
 * @作者: FangLin
 * @创建时间: 2018/12/25 15:15
 * @版本号: V1.0
 */
@ApiModel(value = "Article",description = "文章内容实体")
@Data
public class ArticleContentReq implements Serializable {

    @ApiModelProperty("文章内容ID")
    @JsonIgnore
    private String sacId;
    @ApiModelProperty("文章ID")
    private String articleId;
    @ApiModelProperty("文章内容类型【1：正文 2：图片  3：高级跑马灯 】")
    private String contentType;
    @ApiModelProperty("文章内容")
    private String articleContent;
    @ApiModelProperty("排序")
    private Integer sort;
}
