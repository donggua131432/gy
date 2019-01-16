package com.gy.domain.vo.req.makeup;

import com.gy.domain.vo.req.PageCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @描述: 试装包信息查询条件
 * @作者: DuKai
 * @创建时间: 2018/12/18 18:13
 * @版本号: V1.0
 */
@ApiModel(value = "AppCourseZipQuery",description = "试装包信息查询条件")
@Data
public class AppCourseZipQuery extends PageCondition implements Serializable {

    @ApiModelProperty("试装包名称")
    private String name;

}
