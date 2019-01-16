package com.gy.mapper.makeup;

import com.gy.domain.dto.makeup.FaceMatch;
import com.gy.domain.vo.res.FaceMatchVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FaceMatchMapper {
    int deleteFaceMatchBatch(String faceMatchId);

    int insertFaceMatch(FaceMatch record);

    List<FaceMatchVo> selectFaceMatch();

    int updateFaceMatchById(FaceMatch record);

}