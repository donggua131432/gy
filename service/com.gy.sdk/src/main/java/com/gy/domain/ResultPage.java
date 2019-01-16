package com.gy.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 响应分页信息
 * @作者: DuKai
 * @创建时间: 2018/9/4 17:37
 * @版本号: V3.0
 */

@ApiModel(value = "响应分页信息")
public class ResultPage<T> implements Serializable {
    private static final long serialVersionUID = 8123335363969036940L;

    @ApiModelProperty("总记录数")
    private long total;
    @ApiModelProperty("当前页")
    private int pageNum;
    @ApiModelProperty("总页数")
    private int pages;
    @ApiModelProperty("数据内容列表")
    private List<T> list;

    public ResultPage(){

    }

    public ResultPage(long total, int page, int pageNum, List<T> list){

    }

    public static ResultPage getInstance() {
        return ResultVoHolder.instance.setList(null);
    }

    private static class ResultVoHolder {
        private static ResultPage instance = new ResultPage();
    }

    public long getTotal() {
        return total;
    }

    public ResultPage setTotal(long total) {
        this.total = total;
        return this;
    }

    public int getPages() {
        return pages;
    }

    public ResultPage setPages(int pages) {
        this.pages = pages;
        return this;
    }

    public int getPageNum() {
        return pageNum;
    }

    public ResultPage setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public List<T> getList() {
        return list;
    }

    public ResultPage setList(List<T> list) {
        this.list = list;
        return this;
    }
}
