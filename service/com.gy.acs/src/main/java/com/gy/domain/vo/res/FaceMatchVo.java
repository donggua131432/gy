package com.gy.domain.vo.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @描述: 五官分析匹配返回前端实体
 * @作者: FangLin
 * @创建时间: 2018/12/22 14:32
 * @版本号: V1.0
 */
@ApiModel(value = "FaceMatchVo",description = "五官分析匹配返回前端实体")
@Data
public class FaceMatchVo implements Serializable {
    private static final long serialVersionUID = 6118830959018381400L;
    @ApiModelProperty("器官ID")
    private String organId;
    @ApiModelProperty("器官名称")
    private String organName;
    @ApiModelProperty("特征类型ID")
    private String featureTypeId;
    @ApiModelProperty("特征类型名称")
    private String featureTypeName;
    @ApiModelProperty("特征值ID")
    private String featureId;
    @ApiModelProperty("特征值名称")
    private String featureName;
    @ApiModelProperty("五官分析匹配ID")
    private String faceMatchId;
    @ApiModelProperty("整装ID")
    private String wholeMakeupId;
    @ApiModelProperty("评分【单位/星】")
    private Integer matchScore;
}
