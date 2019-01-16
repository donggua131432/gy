package com.gy.mapper.makeup;

import com.gy.domain.dto.makeup.Article;
import com.gy.domain.vo.req.ArticleReq;

import java.util.List;

public interface ArticleMapper {
    int deleteArticleById(String articleId);

    int insertArticle(ArticleReq recordReq);

    Article selectArticle(String articleId);

    int updateArticleById(ArticleReq articleReq);

}