package com.gy.domain.dto.makeup;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @描述: 重新定义器官实体
 * @作者: FangLin
 * @创建时间: 2018/12/19 11:18
 * @版本号: V1.0
 *  
 */
@ApiModel(value = "OrganDefine",description = "重新定义器官实体")
@Data
public class OrganDefine implements Serializable {
    private static final long serialVersionUID = -6578759965788394256L;

    @ApiModelProperty("ID添加时自动生成,修改不能为空")
    private String organDefineId;

    @ApiModelProperty(value = "器官ID")
    private String organId;
    @ApiModelProperty(value = "器官文艺名")
    private String literaryName;
    @ApiModelProperty(value = "器官Slogan")
    private String organSlogan;
    @ApiModelProperty(value = "器官总结语")
    private String organRemark;

    @ApiModelProperty("创建时间")
    @JSONField(format = "yy-MM-dd HH:mm:ss")
    @JsonIgnore
    private Date createTime;

    @ApiModelProperty(value = "排列组合集合")
    private List<String> organDefineList;

}