package com.gy.service.makeup;

import com.gy.domain.dto.makeup.ArticleContent;
import com.gy.domain.vo.req.ArticleContentReq;
import com.gy.mapper.makeup.ArticleContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 文章内容服务层
 * @作者: FangLin
 * @创建时间: 2018/12/24 17:52
 * @版本号: V1.0
 */
@Service
public class ArticleContentService {
    @Autowired
    private ArticleContentMapper articleContentMapper;

    public List<ArticleContent> listArticleContentBatch(String articleId) {
        return articleContentMapper.selectArticleContent(articleId);
    }

    public int addArticleContent(ArticleContent articleContent) {
        return articleContentMapper.insertArticleContent(articleContent);
    }

    public int addArticleContentBatch(List<ArticleContentReq> articleContentReqList) {
        return articleContentMapper.insertArticleContentBatch(articleContentReqList);
    }

    public int updateArticleContentBatch(List<ArticleContentReq> articleContentReqList) {
        return articleContentMapper.updateArticleContentBatchById(articleContentReqList);
    }

    public int deleteArticleContentBatch(List<String> articleContentIdList) {
        return articleContentMapper.deleteArticleContentBatchById(articleContentIdList);
    }
}
