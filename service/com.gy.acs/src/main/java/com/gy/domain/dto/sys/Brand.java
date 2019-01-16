package com.gy.domain.dto.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 品牌商实体
 * @作者: FangLin
 * @创建时间: 2018/12/19 11:18
 * @版本号: V1.0
 *  
 */

@ApiModel(value = "Brand",description = "品牌商")
@Data
public class Brand implements Serializable {

    private static final long serialVersionUID = 7004466664288972128L;
    @ApiModelProperty("brandID")
    private String brandId;
    @ApiModelProperty("品牌商名")
    private String brandName;
    @ApiModelProperty("创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty("文件地址")
    private String filePath;
    @ApiModelProperty("文件ID")
    private String fileId;

}