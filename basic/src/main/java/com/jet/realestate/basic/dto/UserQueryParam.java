package com.jet.realestate.basic.dto;

import lombok.Data;

@Data
public class UserQueryParam {
    private Long roleId;

    private String username;

    private String phone;

    protected int pageSize;
    protected int pageNum;

    private int offset;

    protected int limit;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }


    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }

    public int getLimit() {
        return pageSize;
    }
}
