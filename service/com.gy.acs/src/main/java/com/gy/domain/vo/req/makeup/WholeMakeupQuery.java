package com.gy.domain.vo.req.makeup;

import com.gy.domain.vo.req.PageCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @描述: 整装库分页查询实体
 * @作者: FangLin
 * @创建时间: 2018/12/21 16:28
 * @版本号: V1.0
 */
@ApiModel(value = "WholeMakeupQuery",description = "整装库分页查询实体")
@Data
public class WholeMakeupQuery extends PageCondition implements Serializable {
    private static final long serialVersionUID = -3515262589671926623L;
    @ApiModelProperty("整装库名称")
    private String wholeMakeupName;
}
