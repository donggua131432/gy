package com.gy.mapper.makeup;

import com.gy.domain.dto.sys.Kol;
import com.gy.domain.vo.req.KolQuery;
import com.gy.domain.vo.req.PageCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KolMapper {
    int deleteKolById(String kolId);

    int insertKol(@Param("kolId") String kolId,@Param("kolName") String kolName);

    List<Kol> selectKolPage(KolQuery kolQuery);

    int updateKolById(Kol record);

}