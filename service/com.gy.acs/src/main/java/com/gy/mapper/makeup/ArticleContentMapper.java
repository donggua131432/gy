package com.gy.mapper.makeup;

import com.gy.domain.dto.makeup.ArticleContent;
import com.gy.domain.vo.req.ArticleContentReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleContentMapper {
    int deleteArticleContentBatchById(@Param("sacIdList") List<String> sacIdList);

    int insertArticleContent(ArticleContent record);

    int insertArticleContentBatch(@Param("articleContentReqList") List<ArticleContentReq> articleContentReqList);

    int getLamp();

    List<ArticleContent> selectArticleContent(String sacId);

    int updateArticleContentBatchById(@Param("articleContentReqList") List<ArticleContentReq> articleContentReqList);

}