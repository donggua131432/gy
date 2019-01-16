package com.gy.service.makeup;

import com.gy.domain.FileInfo;
import com.gy.domain.dto.sys.FileInfoRel;
import com.gy.mapper.sys.FileInfoRelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 文件业务关系数据服务层
 * @作者: FangLin
 * @创建时间: 2018/12/19 21:50
 * @版本号: V1.0
 */
@Service
public class FileInfoRelService {
    @Autowired
    private FileInfoRelMapper fileInfoRelMapper;

    public FileInfoRel listFileInfoRel(String id) {
        return fileInfoRelMapper.selectFileInfoRel(id);
    }
    //待添加 删除时改变文件状态
    public int addFileInfoRel(FileInfoRel fileInfoRel) {
        return fileInfoRelMapper.insertFileInfoRel(fileInfoRel);
    }

    public int addFileInfoRelBatch(List<FileInfoRel> fileInfoRelList) {
        return fileInfoRelMapper.insertFileInfoRelBatch(fileInfoRelList);
    }

    public int updateFileInfoRel(FileInfoRel fileInfoRel) {
        return fileInfoRelMapper.updateFileInfoRelById(fileInfoRel);
    }

    public int updateFileInfoRelBatch(List<FileInfoRel> fileInfoRelList) {
        return fileInfoRelMapper.updateFileInfoRelBatchById(fileInfoRelList);
    }

    public int deleteFileInfoRel(String fileId) {
        return fileInfoRelMapper.deleteFileInfoRelById(fileId);
    }

    public int deleteFileInfoRelBatch(List<String> fileIdList) {
        return fileInfoRelMapper.deleteFileInfoRelByIdBatch(fileIdList);
    }
}
