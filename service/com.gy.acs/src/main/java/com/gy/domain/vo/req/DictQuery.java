package com.gy.domain.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  * @描述: ${DESCRIPTION}
 *  * @作者: FangLin
 *  * @创建时间: 2018/11/30 18:49
 *  * @版本号: V1.0
 *  
 */
@ApiModel(value = "DictQuery",description = "字典查询实体")
@Data
public class DictQuery extends PageCondition implements Serializable {

    private static final long serialVersionUID = 8989320342582281879L;
    @ApiModelProperty("字典编码")
    private String dictCode;
    @ApiModelProperty("字典名称")
    private String dictName;
}
