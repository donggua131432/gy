package com.gy.service.makeup;

import com.gy.domain.dto.makeup.Article;
import com.gy.domain.vo.req.ArticleReq;
import com.gy.mapper.makeup.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述: 文章服务层
 * @作者: FangLin
 * @创建时间: 2018/12/24 17:46
 * @版本号: V1.0
 */
@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    public Article getArticle(String wholeMakeupId) {
        return articleMapper.selectArticle(wholeMakeupId);
    }

    public int addArticle(ArticleReq articleReq) {
        return articleMapper.insertArticle(articleReq);
    }

    public int updateArticle(ArticleReq articleReq) {
        return articleMapper.updateArticleById(articleReq);
    }

    public int deleteArticle(String articleId) {
        return articleMapper.deleteArticleById(articleId);
    }


}
