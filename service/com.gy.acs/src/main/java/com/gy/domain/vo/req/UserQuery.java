package com.gy.domain.vo.req;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  * @描述: ${DESCRIPTION}
 *  * @作者: FangLin
 *  * @创建时间: 2018/12/6 17:28
 *  * @版本号: V1.0
 *  
 */
@ApiModel(value = "UserQuery",description = "用户查询实体")
@Data
public class UserQuery extends PageCondition implements Serializable {

    private static final long serialVersionUID = 7592285921834808615L;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("部门ID")
    private String deptId;


}
