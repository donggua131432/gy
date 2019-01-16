package com.gy.domain.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @描述: 分页实体
 * @作者: DuKai
 * @创建时间: 2018/11/26 20:12
 * @版本号: V1.0
 */
@ApiModel(value = "PageCondition",description = "分页")
@Data
public class PageCondition implements Serializable {
    private static final long serialVersionUID = -807024126835817805L;

    @ApiModelProperty(value="当前页",required=true)
    private int page = 1;
    @ApiModelProperty(value="每页大小",required=true)
    private int size = 10;
}
