package com.gy.domain.vo.res;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "OrganDefineVo",description = "重新定义器官名实体")
@Data
public class OrganDefineVo implements Serializable {
    private static final long serialVersionUID = -6831841151038177630L;

    @ApiModelProperty("重新定义器官名ID")
    private String organDefineId;
    @ApiModelProperty("器官ID")
    private String organId;
    @ApiModelProperty("器官名")
    private String organName;
    @ApiModelProperty("器官文艺名")
    private String literaryName;
    @ApiModelProperty("器官Slogan")
    private String organSlogan;
    @ApiModelProperty("器官总结语")
    private String organRemark;
    @ApiModelProperty("创建时间")
    @JSONField(format = "yy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("重新定义器官名排列组合详情集合")
    private List<OrganDefineArrayVo> organDefineArrayVoList;


}