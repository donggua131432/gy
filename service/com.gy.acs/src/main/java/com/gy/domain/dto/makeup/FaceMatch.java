package com.gy.domain.dto.makeup;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "FaceMatch",description = "五官分析匹配实体")
@Data
public class FaceMatch implements Serializable {
    private static final long serialVersionUID = 4238773580643910755L;
    @ApiModelProperty("五官分析匹配ID")
    private String faceMatchId;
    @ApiModelProperty("整装ID")
    private String wholeMakeupId;
    @ApiModelProperty("器官特征ID")
    private String featureId;
    @ApiModelProperty("评分【单位/星】")
    private Integer matchScore;

}