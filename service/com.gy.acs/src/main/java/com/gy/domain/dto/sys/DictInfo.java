package com.gy.domain.dto.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @描述: 数据字典信息实体
 * @作者: DuKai
 * @创建时间: 2018/12/19 10:50
 * @版本号: V1.0
 */

@Data
public class DictInfo implements Serializable {
    private static final long serialVersionUID = -3535782575851590982L;
    @ApiModelProperty("数据字典编码")
    private String dictCode;
    @ApiModelProperty("数据字典名称")
    private String dictName;
    private List<Map<String, String>> itemList;
}
