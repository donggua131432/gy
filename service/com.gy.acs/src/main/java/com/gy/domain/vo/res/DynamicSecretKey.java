package com.gy.domain.vo.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @描述: 动态秘钥
 * @作者: DuKai
 * @创建时间: 2018/11/21 19:08
 * @版本号: V1.0
 */
@ApiModel(value = "DynamicSecretKey",description = "动态秘钥")
@Data
public class DynamicSecretKey implements Serializable {
    private static final long serialVersionUID = -6567204524122846418L;

    @ApiModelProperty("用户key")
    private String userKey;
    @ApiModelProperty("动态密码加密盐")
    private String tokenKey;
}
