package com.gy.domain.dto.makeup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @描述: 器官名排列组合实体
 * @作者: FangLin
 * @创建时间: 2018/12/19 11:18
 * @版本号: V1.0
 *  
 */
@ApiModel(value = "OrganDefineArray",description = "器官名排列组合实体")
@Data
public class OrganDefineArray implements Serializable {
    private static final long serialVersionUID = -1777485863023445220L;

    @ApiModelProperty("排列组合详情ID")
    private String sodaId;
    @ApiModelProperty("重新定义器官名ID")
    private String organDefineId;
    @ApiModelProperty("特征类型ID")
    private String featureTypeId;
    @ApiModelProperty("器官特征值ID")
    private String featureId;
}
