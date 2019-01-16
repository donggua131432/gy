package com.gy.controller;

import com.gy.domain.ReturnInfo;
import com.gy.domain.dto.makeup.VideoSub;
import com.gy.enums.ErrorCodeEnum;
import com.gy.service.makeup.VideoSubService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 视频分段信息接口
 * @作者: FangLin
 * @创建时间: 2018/12/22 17:51
 * @版本号: V1.0
 */
@RestController
@Api(tags={"整装库接口"}, description = "WholeMakeup-API")
@RequestMapping("/videoSub")
public class VideoSubController {
    @Autowired
    private VideoSubService videoSubService;

    @ApiOperation(value = "视频分段信息操作")
    @PutMapping("/operatingVideoSub")
    @Transactional
    public ReturnInfo updateVideoSub(@ApiParam(name = "VideoSub", value = "视频分段信息实体", required = true) @RequestBody List<VideoSub> videoSub) {
        int flag = 0;
        List<VideoSub> tempList = new ArrayList<>();
        tempList.addAll(videoSub);
        //获取数据库的分段视频信息
        String fileId = videoSub.get(0).getFileId();
        List<VideoSub> dateBaseVideoSub = videoSubService.listVideoSub(fileId);
        //取两集合交集
        tempList.retainAll(dateBaseVideoSub);
        //更新交集的内容
        if(tempList.size() > 0) videoSubService.updateVideoSubBatch(tempList);

        //批量添加视频分段信息
        videoSub.removeAll(tempList);
        if (videoSub.size() > 0) videoSubService.addVideoSubBatch(videoSub);

        //批量删除视频分段信息
        dateBaseVideoSub.removeAll(tempList);
        if (dateBaseVideoSub.size() > 0) videoSubService.deleteVideoSubBatch(dateBaseVideoSub);

        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(null);
    }
}
