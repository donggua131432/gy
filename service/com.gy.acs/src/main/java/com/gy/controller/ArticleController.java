package com.gy.controller;

import com.gy.domain.ReturnInfo;
import com.gy.domain.dto.makeup.Article;
import com.gy.domain.dto.makeup.ArticleContent;
import com.gy.domain.dto.makeup.Lamp;
import com.gy.domain.dto.sys.FileInfoRel;
import com.gy.domain.vo.req.ArticleContentReq;
import com.gy.domain.vo.req.ArticleReq;
import com.gy.enums.ErrorCodeEnum;
import com.gy.service.makeup.ArticleContentService;
import com.gy.service.makeup.ArticleService;
import com.gy.service.makeup.FileInfoRelService;
import com.gy.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 文章接口
 * @作者: FangLin
 * @创建时间: 2018/12/24 18:18
 * @版本号: V1.0
 */
@RestController
@Api(tags={"整装库接口"}, description = "WholeMakeup-API")
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private FileInfoRelService fileInfoRelService;
    @Autowired
    private ArticleContentService articleContentService;

    @ApiOperation(value = "查询文章")
    @GetMapping("/getArticle")
    public ReturnInfo getArticle(@ApiParam(name = "wholeMakeupId", value = "整装库ID", required = true) String wholeMakeupId) {
        Article article = articleService.getArticle(wholeMakeupId);
        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(article);
    }

    @ApiOperation(value = "添加文章")
    @Transactional
    @PostMapping("/addArticle")
    public ReturnInfo addArticle(@ApiParam(name = "ArticleReq", value = "文章对象", required = true) @RequestBody Article article) {
        //添加文章
            //生成文章ID
        String articleId = StringUtil.UUID();
        ArticleReq articleReq = new ArticleReq();
        articleReq.setArticleId(articleId);
        articleReq.setWholeMakeupId(article.getWholeMakeupId());
        articleReq.setArticleTitle(article.getArticleTitle());
        articleReq.setLinkText(article.getLinkText());
        articleReq.setLinkUrl(article.getLinkUrl());
        articleService.addArticle(articleReq);
        //添加封页图信息
        FileInfoRel fileInfoRelCoverPage = new FileInfoRel();
        fileInfoRelCoverPage.setRelId(articleId);
        fileInfoRelCoverPage.setFileType("4");
        fileInfoRelCoverPage.setFileId(article.getImageId());
        fileInfoRelCoverPage.setFileOrder(article.getSort());
        fileInfoRelService.addFileInfoRel(fileInfoRelCoverPage);
        //添加文章内容
            //获取所以文件内容信息
        List<ArticleContent> articleContents = new ArrayList<>();
        articleContents.addAll(article.getArticleContentList());
            //单纯文章内容信息集合
        List<ArticleContentReq> articleContentReqList = new ArrayList<>();
            //文件业务关系信息集合
        List<FileInfoRel> fileInfoRelList = new ArrayList<>();
        for (ArticleContent articleContent : articleContents) {
            ArticleContentReq articleContentReq = new ArticleContentReq();

            //文本
            if ("1".equals(articleContent.getContentType())) {
                String articleContentId = StringUtil.UUID();
                articleContentReq.setSacId(articleContentId);
                articleContentReq.setArticleId(articleId);
                articleContentReq.setContentType("1");
                articleContentReq.setArticleContent(articleContent.getArticleContent());
                articleContentReq.setSort(articleContent.getSort());
            }
            //图片
            if ("2".equals(articleContent.getContentType())) {
                String articleContentId = StringUtil.UUID();
                articleContentReq.setSacId(articleContentId);
                articleContentReq.setArticleId(articleId);
                articleContentReq.setContentType("2");
                articleContentReq.setSort(articleContent.getSort());

                FileInfoRel fileInfoRe1Picture = new FileInfoRel();
                fileInfoRe1Picture.setRelId(articleContentId);
                fileInfoRe1Picture.setFileId(articleContent.getFileId());
                fileInfoRe1Picture.setFileType("4");
                fileInfoRelList.add(fileInfoRe1Picture);
            }
            //跑马灯
            if ("3".equals(articleContent.getContentType())) {
                String articleContentId = StringUtil.UUID();
                articleContentReq.setSacId(articleContentId);
                articleContentReq.setArticleId(articleId);
                articleContentReq.setContentType("3");
                articleContentReq.setSort(articleContent.getSort());

                List<FileInfoRel> fileInfoRelListAdd = new ArrayList<>();
                for (Lamp lamp : articleContent.getLampList()) {
                    FileInfoRel fileInfoRel = new FileInfoRel();
                    fileInfoRel.setRelId(articleContentId);
                    fileInfoRel.setFileId(lamp.getFileId());
                    fileInfoRel.setFileType("6");
                    fileInfoRel.setFileOrder(lamp.getSort());
                    fileInfoRelListAdd.add(fileInfoRel);
                }
                fileInfoRelList.addAll(fileInfoRelListAdd);
            }
            articleContentReqList.add(articleContentReq);
        }
        if (!fileInfoRelList.isEmpty()) {
            fileInfoRelService.addFileInfoRelBatch(fileInfoRelList);
        }
        if (!articleContentReqList.isEmpty()) {
            articleContentService.addArticleContentBatch(articleContentReqList);
        }
        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(null);
    }

    @ApiOperation(value = "更新文章")
    @PutMapping("/updateArticle")
    public ReturnInfo updateArticle(@ApiParam(name = "ArticleReq", value = "文章对象", required = true) @RequestBody Article article) {
        //更新文章
        ArticleReq articleReq = new ArticleReq();
        articleReq.setArticleId(article.getArticleId());
        articleReq.setWholeMakeupId(article.getWholeMakeupId());
        articleReq.setArticleTitle(article.getArticleTitle());
        articleReq.setLinkText(article.getLinkText());
        articleReq.setLinkUrl(article.getLinkUrl());
        articleService.updateArticle(articleReq);
        //更新封页图信息
        FileInfoRel fileInfoRelCoverPage = new FileInfoRel();
        fileInfoRelCoverPage.setRelId(article.getArticleId());
        fileInfoRelCoverPage.setFileType("4");
        fileInfoRelCoverPage.setFileId(article.getImageId());
        fileInfoRelCoverPage.setFileOrder(article.getSort());
        fileInfoRelService.updateFileInfoRel(fileInfoRelCoverPage);

        List<ArticleContent> tempList = new ArrayList<>();
        //获取前端的文章内容集合
        List<ArticleContent> articleContentList = new ArrayList<>();
        articleContentList.addAll(article.getArticleContentList());
        //获取数据库的文章内容集合
        List<ArticleContent> dateBaseArticleContentList = articleContentService.listArticleContentBatch(article.getArticleId());
        tempList.addAll(articleContentList);
        //取交集
        tempList.retainAll(dateBaseArticleContentList);

        //更新要更新的tempList
        List<ArticleContentReq> updateArticleContentList = new ArrayList<>();
        List<FileInfoRel> updateFileInfoRelList = new ArrayList<>();
        for (ArticleContent articleContents : tempList) {
            ArticleContentReq articleContentReq = new ArticleContentReq();

            //文本
            if ("1".equals(articleContents.getContentType())) {
                articleContentReq.setSacId(articleContents.getSacId());
                articleContentReq.setArticleId(articleContents.getArticleId());
                articleContentReq.setContentType("1");
                articleContentReq.setArticleContent(articleContents.getArticleContent());
                articleContentReq.setSort(articleContents.getSort());
            }
            //图片
            if ("2".equals(articleContents.getContentType())) {
                articleContentReq.setSacId(articleContents.getSacId());
                articleContentReq.setArticleId(articleContents.getArticleId());
                articleContentReq.setContentType("2");
                articleContentReq.setSort(articleContents.getSort());

                FileInfoRel fileInfoRe1Picture = new FileInfoRel();
                fileInfoRe1Picture.setRelId(articleContents.getSacId());
                fileInfoRe1Picture.setFileId(articleContents.getFileId());
                fileInfoRe1Picture.setFileType("4");
                updateFileInfoRelList.add(fileInfoRe1Picture);
            }
            //跑马灯
            if ("3".equals(articleContents.getContentType())) {
                articleContentReq.setSacId(articleContents.getSacId());
                articleContentReq.setArticleId(articleContents.getArticleId());
                articleContentReq.setContentType("3");
                articleContentReq.setSort(articleContents.getSort());

                List<FileInfoRel> fileInfoRelListAdd = new ArrayList<>();
                for (Lamp lamp : articleContents.getLampList()) {
                    FileInfoRel fileInfoRel = new FileInfoRel();
                    fileInfoRel.setRelId(articleContents.getSacId());
                    fileInfoRel.setFileId(lamp.getFileId());
                    fileInfoRel.setFileType("6");
                    fileInfoRel.setFileOrder(lamp.getSort());
                    fileInfoRelListAdd.add(fileInfoRel);
                }
                updateFileInfoRelList.addAll(fileInfoRelListAdd);
            }
            updateArticleContentList.add(articleContentReq);
        }
            articleContentService.updateArticleContentBatch(updateArticleContentList);
            fileInfoRelService.updateFileInfoRelBatch(updateFileInfoRelList);

            //添加新添加的
            articleContentList.removeAll(tempList);
            List<ArticleContentReq> addArticleContentReq = new ArrayList<>();
            List<FileInfoRel> addFileInfoRel = new ArrayList<>();
            for (ArticleContent articleContentAdd : articleContentList) {
                ArticleContentReq articleContentReqAdd = new ArticleContentReq();

                //文本
                if ("1".equals(articleContentAdd.getContentType())) {
                    String articleContentId = StringUtil.UUID();
                    articleContentReqAdd.setSacId(articleContentId);
                    articleContentReqAdd.setArticleId(articleContentAdd.getArticleId());
                    articleContentReqAdd.setContentType("1");
                    articleContentReqAdd.setArticleContent(articleContentAdd.getArticleContent());
                    articleContentReqAdd.setSort(articleContentAdd.getSort());
                }
                //图片
                if ("2".equals(articleContentAdd.getContentType())) {
                    String articleContentId = StringUtil.UUID();
                    articleContentReqAdd.setSacId(articleContentId);
                    articleContentReqAdd.setArticleId(articleContentAdd.getArticleId());
                    articleContentReqAdd.setContentType("2");
                    articleContentReqAdd.setSort(articleContentAdd.getSort());

                    FileInfoRel fileInfoRe1PictureAdd = new FileInfoRel();
                    fileInfoRe1PictureAdd.setRelId(articleContentId);
                    fileInfoRe1PictureAdd.setFileId(articleContentAdd.getFileId());
                    fileInfoRe1PictureAdd.setFileType("4");
                    addFileInfoRel.add(fileInfoRe1PictureAdd);
                }
                //跑马灯
                if ("3".equals(articleContentAdd.getContentType())) {
                    String articleContentId = StringUtil.UUID();
                    articleContentReqAdd.setSacId(articleContentId);
                    articleContentReqAdd.setArticleId(articleContentAdd.getArticleId());
                    articleContentReqAdd.setContentType("3");
                    articleContentReqAdd.setSort(articleContentAdd.getSort());

                    List<FileInfoRel> fileInfoRelListAdd = new ArrayList<>();
                    for (Lamp lamp : articleContentAdd.getLampList()) {
                        FileInfoRel fileInfoRel = new FileInfoRel();
                        fileInfoRel.setRelId(articleContentId);
                        fileInfoRel.setFileId(lamp.getFileId());
                        fileInfoRel.setFileType("6");
                        fileInfoRel.setFileOrder(lamp.getSort());
                        fileInfoRelListAdd.add(fileInfoRel);
                    }
                    addFileInfoRel.addAll(fileInfoRelListAdd);
                }
                addArticleContentReq.add(articleContentReqAdd);
            }
            articleContentService.addArticleContentBatch(addArticleContentReq);
            fileInfoRelService.addFileInfoRelBatch(addFileInfoRel);

            //删除要删除的
            dateBaseArticleContentList.removeAll(tempList);
            List<String> deleteArticleContentReq = new ArrayList<>();
            List<String> deleteFileInfoRelList = new ArrayList<>();
            for (ArticleContent articleContentDelete : dateBaseArticleContentList) {
                //文本
                if ("1".equals(articleContentDelete.getContentType())) {
                    deleteArticleContentReq.add(articleContentDelete.getSacId());
                }
                //图片
                if ("2".equals(articleContentDelete.getContentType())) {
                    deleteArticleContentReq.add(articleContentDelete.getSacId());
                    deleteFileInfoRelList.add(articleContentDelete.getFileId());
                }
                //跑马灯
                if ("3".equals(articleContentDelete.getContentType())) {
                    deleteArticleContentReq.add(articleContentDelete.getSacId());
                    List<String> deleteFileInfoRels = new ArrayList<>();
                    for (Lamp lamp : articleContentDelete.getLampList()) {
                        deleteFileInfoRels.add(lamp.getFileId());
                    }
                    deleteFileInfoRelList.addAll(deleteFileInfoRels);
                }
            }
            articleContentService.deleteArticleContentBatch(deleteArticleContentReq);
            fileInfoRelService.deleteFileInfoRelBatch(deleteFileInfoRelList);

        return ReturnInfo.getInstance().setResult(ErrorCodeEnum.SUCCESS).setData(null);
    }

}
