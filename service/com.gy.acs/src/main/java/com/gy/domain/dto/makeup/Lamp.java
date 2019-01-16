package com.gy.domain.dto.makeup;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @描述: 高级跑马灯实体
 * @作者: FangLin
 * @创建时间: 2018/12/25 14:41
 * @版本号: V1.0
 */
@ApiModel(value = "Lamp",description = "高级跑马灯实体")
@Data
public class Lamp {
    @ApiModelProperty("跑马灯图片ID")
    private String fileId;
    @ApiModelProperty("跑马灯图片地址")
    private String fileAddress;
    @ApiModelProperty("跑马灯排序")
    private int sort;
}
