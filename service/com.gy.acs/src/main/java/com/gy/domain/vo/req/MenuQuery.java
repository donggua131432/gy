package com.gy.domain.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @描述: 菜单查询
 * @作者: FangLin
 * @创建时间: 2018/11/26 14:46
 * @版本号: V1.0
 *  
 */
@ApiModel(value = "MenuQuery",description = "菜单查询实体")
@Data
public class MenuQuery extends PageCondition implements Serializable {
    private static final long serialVersionUID = 172818291420000839L;

    @ApiModelProperty("菜单名称")
    private String menuName;
    @ApiModelProperty("菜单编码")
    private String menuCode;

}
