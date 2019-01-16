package com.gy.mapper.makeup;

import com.gy.domain.dto.makeup.VideoSub;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoSubMapper {
    int insertVideoSubBatch(@Param("videoSubList") List<VideoSub> videoSubList);

    List<VideoSub> selectVideoSub(String fileId);

    int updateVideoSubByIdBatch(@Param("videoSubList") List<VideoSub> videoSubList);

    int deleteVideoSubBatch(@Param("videoSubList") List<VideoSub> videoSubIdList);

    int deleteVideoSubById(String videoSubId);
}