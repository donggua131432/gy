package com.gy.domain.dto.makeup;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@ApiModel(value = "VideoSub",description = "视频分段信息实体")
@Data
public class VideoSub implements Serializable {

    private static final long serialVersionUID = -9215552420469771968L;

    @ApiModelProperty("视频分段ID")
    private String videoSubId;
    @ApiModelProperty("视频文件ID")
    private String fileId;
    @ApiModelProperty("结束时间点")
    private Integer endTime;
    @ApiModelProperty("步骤名")
    private String stepName;
    @ApiModelProperty("试装包ID")
    private String zipId;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("创建时间")
    @JSONField(format = "yy-HH-dd hh:MM:ss")
    private Date createTime;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoSub videoSub = (VideoSub) o;
        return Objects.equals(videoSubId, videoSub.videoSubId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(videoSubId);
    }
}