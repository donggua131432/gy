package com.gy.mapper.sys;

import com.gy.domain.dto.sys.FileInfoRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileInfoRelMapper {
    int deleteFileInfoRelById(String keyId);

    int deleteFileInfoRelByIdBatch(@Param("fileInfoRelIdList") List<String> fileInfoRelIdList);

    int insertFileInfoRel(FileInfoRel record);

    int insertFileInfoRelBatch(@Param("fileInfoRelList") List<FileInfoRel> fileInfoRelList);

    FileInfoRel selectFileInfoRel(String id);

    int updateFileInfoRelBatchById(@Param("fileInfoRelList") List<FileInfoRel> fileInfoRelList);

    int updateFileInfoRelById(FileInfoRel fileInfoRel);

}
