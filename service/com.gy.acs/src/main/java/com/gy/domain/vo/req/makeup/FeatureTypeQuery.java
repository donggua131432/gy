package com.gy.domain.vo.req.makeup;

import com.gy.domain.vo.req.PageCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @描述: 器官特征类型查询实体
 * @作者: FangLin
 * @创建时间: 2018/12/14 15:57
 * @版本号: V1.0
 */
@ApiModel(value = "FeatureTypeQuery",description = "器官特征类型查询实体")
@Data
public class FeatureTypeQuery extends PageCondition implements Serializable {

    private static final long serialVersionUID = 4207203330922840287L;

    @ApiModelProperty("器官ID")
    private String organId;

}
