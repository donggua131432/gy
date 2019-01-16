package com.gy.mongo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MongoPageWrapper<T> implements Serializable {
    private static final long serialVersionUID = -3982035973806819917L;

    private int page;
    private int totalPage;
    private long total;
    private int fromIndex;
    private int toIndex;
    private List<T> rows;
    private int pageSize;

    public MongoPageWrapper(int page, int pageSize, long total, List<T> rows) {
        this.pageSize = pageSize;
        this.total = total;

        if (this.total > 0 && pageSize > 0) {
            this.totalPage = ((Double) Math.ceil((double) this.total / pageSize)).intValue();
        }

        this.page = page;
        if (this.page > this.totalPage) {
            this.page = this.totalPage;
        }

        this.fromIndex = (this.page - 1) * pageSize;
        if (this.fromIndex < 0) {
            this.fromIndex = 0;
        }
        this.toIndex = this.fromIndex + pageSize;

        if (this.toIndex > this.total) {
            this.toIndex = (int) this.total;
        }

        this.rows = rows;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public int getToIndex() {
        return toIndex;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Date getCurrTime() {
        return new Date();
    }
}
