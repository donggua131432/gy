package com.gy.mongo;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @描述:  mongodb 的基类
 * @作者: DuKai
 * @创建时间: 2018/12/18 15:15
 * @版本号: V1.0
 */

public interface MongoDBDao  {

    /**
     * 根据主键查询一个对象
     * @param id 主键
     * @param entityClass 对象的类型
     * @return 对象实例
     */
    <T> T findById(String id, Class<T> entityClass);

    /**
     * 根据主键查询一个对象
     * @param id 主键
     * @param entityClass 对象的类型
     * @param collection 表名
     * @param <T>
     * @return
     */
    <T> T findById(String id, Class<T> entityClass, String collection);

    /**
     * 根据主键查询一个对象
     * @param query 查询条件
     * @param entityClass 对象的类型
     * @return 对象实例
     */
    <T> T findOne(Query query, Class<T> entityClass);

    /**
     * 根据主键查询一个对象
     * @param query 查询条件
     * @param entityClass 对象的类型
     * @return 对象实例
     */
    <T> T findOne(Query query, Class<T> entityClass, String collection);

      /**
     * 通过条件查询实体(集合)
     * @param query 查询条件
     * @param entityClass 对象的类型
     */
    <T> List<T> find(Query query, Class<T> entityClass) ;

    /**
     * 通过条件查询实体(集合)
     * @param query 查询条件
     * @param entityClass 对象的类型
     */
    <T> List<T> find(Query query, Class<T> entityClass, String collection) ;

    /**
     * 通过条件查询实体(集合)的所有元素
     *
     * @param entityClass 对象的类型
     */
    <T> List<T> findAll(Class<T> entityClass) ;

    /**
     * 通过条件查询更新数据
     *
     * @param query
     * @param update
     * @return
     */
    <T> void update(Query query, Update update, Class<T> entityClass) ;

    /**
     * 保存一个对象到mongodb
     *
     * @param entity
     * @return
     */
    <T> void save(T entity) ;

    /**
     * 求数据总和
     * @param query
     * @return
     */
    public <T> long count(Query query, Class<T> entityClass);
    public <T> long count(Query query, Class<T> entityClass, String collection);

    /**
     * 保存一个指定对象到mongodb
     *
     * @param entity
     * @return
     */
    <T> void saveTable(T entity, String collectionName) ;
}
