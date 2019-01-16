package com.gy.domain.dto.makeup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@ApiModel(value = "ArticleContent",description = "文章内容综合接收实体")
@Data
public class ArticleContent implements Serializable {
    private static final long serialVersionUID = 4004085059021727373L;
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
    //图片信息
    @ApiModelProperty("图片ID")
    private String fileId;
    @ApiModelProperty("图片地址")
    private String fileAddress;
    //跑马灯信息
    @ApiModelProperty("高级跑马灯图片集合")
    private List<Lamp> lampList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleContent that = (ArticleContent) o;
        return Objects.equals(sacId, that.sacId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sacId);
    }
}