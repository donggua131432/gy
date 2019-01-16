package com.gy.domain.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @描述: Kol查询实体
 * @作者: FangLin
 * @创建时间: 2018/12/27 17:06
 * @版本号: V1.0
 */
@ApiModel(value = "KolQuery",description = "kol查询实体")
@Data
public class KolQuery extends PageCondition implements Serializable {
    private static final long serialVersionUID = -3277564483590720470L;
    @ApiModelProperty("Kol名称")
    private String kolName;
}
