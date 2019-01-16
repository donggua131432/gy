package com.gy.domain.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @描述: 文件信息表分页查询实体
 * @作者: FangLin
 * @创建时间: 2018/12/21 11:28
 * @版本号: V1.0
 */
@ApiModel(value = "SysFileInfoQuery",description = "文件信息表分页查询实体")
@Data
public class SysFileInfoQuery extends PageCondition implements Serializable {


    private static final long serialVersionUID = 6784182888655434836L;
    @ApiModelProperty("文件名称")
    private String fileName;

}
