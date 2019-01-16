package com.gy.service.makeup;

import com.gy.domain.dto.mongo.AppCourseZip;
import com.gy.domain.vo.req.makeup.AppCourseZipQuery;
import com.gy.mongo.MongoDBDao;
import com.gy.mongo.MongoPageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @描述: 试装包获取
 * @作者: DuKai
 * @创建时间: 2018/12/17 18:27
 * @版本号: V1.0
 */

@Service
public class TryMakeupService {

    @Autowired
    private MongoDBDao mongoDBDao;

    /**
     * 根据试装包Id获取试装包信息
     * @param zipId
     * @return
     */
    public AppCourseZip getAppCourseZip(String zipId){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(zipId));
        AppCourseZip appCourseZip = mongoDBDao.findOne(query, AppCourseZip.class,"appcoursezip");
        return appCourseZip;
    }


    /**
     * 获取分页试妆包列表
     * @return
     */
    public MongoPageWrapper<AppCourseZip> listAppCourseZip(AppCourseZipQuery appCourseZipQuery){
        Query query = new Query();
        query.addCriteria(Criteria.where("enabled").is(1));
        if(!"".equals(appCourseZipQuery.getName()) && appCourseZipQuery.getName() !=null){
            query.addCriteria(Criteria.where("name").regex(appCourseZipQuery.getName()));
        }
        int count =(int)(mongoDBDao.count(query, AppCourseZip.class,"appcoursezip"));
        //获取分页信息
        MongoPageWrapper<AppCourseZip> pageWrapper =  new MongoPageWrapper(appCourseZipQuery.getPage(), appCourseZipQuery.getSize(), count, null);
        //查询显示记录条数
        query.limit(appCourseZipQuery.getSize()).skip(pageWrapper.getFromIndex());
        //排序
        query.with(new Sort(Sort.Direction.DESC, "createDate"));
        List<AppCourseZip> appCourseZipList = mongoDBDao.find(query, AppCourseZip.class, "appcoursezip");
        pageWrapper.setRows(appCourseZipList);
        return pageWrapper;
    }

}
