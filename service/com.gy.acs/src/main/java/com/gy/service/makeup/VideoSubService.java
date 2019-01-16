package com.gy.service.makeup;

import com.gy.domain.dto.makeup.VideoSub;
import com.gy.mapper.makeup.VideoSubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 视频分段信息服务层
 * @作者: FangLin
 * @创建时间: 2018/12/22 17:40
 * @版本号: V1.0
 */
@Service
public class VideoSubService {
    @Autowired
    private VideoSubMapper videoSubMapper;

    public List<VideoSub> listVideoSub(String fileId) {
        return videoSubMapper.selectVideoSub(fileId);
    }

    public int addVideoSubBatch(List<VideoSub> videoSub) {
        return videoSubMapper.insertVideoSubBatch(videoSub);
    }

    public int updateVideoSubBatch(List<VideoSub> videoSub) {
        return videoSubMapper.updateVideoSubByIdBatch(videoSub);
    }

    public int deleteVideoSubBatch(List<VideoSub> videoSubIdList) {
        return videoSubMapper.deleteVideoSubBatch(videoSubIdList);
    }

    public int deleteVideoSubById(String videoSubId) {
        return videoSubMapper.deleteVideoSubById(videoSubId);
    }
}
