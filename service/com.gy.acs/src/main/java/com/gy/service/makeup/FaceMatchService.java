package com.gy.service.makeup;

import com.gy.domain.dto.makeup.FaceMatch;
import com.gy.domain.vo.res.FaceMatchVo;
import com.gy.mapper.makeup.FaceMatchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 五官分析匹配服务层
 * @作者: FangLin
 * @创建时间: 2018/12/22 11:42
 * @版本号: V1.0
 */
@Service
public class FaceMatchService {
    @Autowired
    private FaceMatchMapper faceMatchMapper;

    public List<FaceMatchVo> listFaceMatch() {
        return faceMatchMapper.selectFaceMatch();
    }

    public int addFaceMatch(FaceMatch faceMatch) {
        return faceMatchMapper.insertFaceMatch(faceMatch);
    }

    public int updateFaceMatch(FaceMatch faceMatch) {
        return faceMatchMapper.updateFaceMatchById(faceMatch);
    }

    public int deleteFaceMatch(String faceMatchId) {
        return faceMatchMapper.deleteFaceMatchBatch(faceMatchId);
    }
}
