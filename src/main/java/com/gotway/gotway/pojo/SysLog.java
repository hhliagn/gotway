package com.gotway.gotway.pojo;

import java.util.Date;

public class SysLog {
    private Integer id;

    private Integer userId;

    private Date createTime;

    private String theme;

    private String operation;

    private String objectTag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme == null ? null : theme.trim();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public String getObjectTag() {
        return objectTag;
    }

    public void setObjectTag(String objectTag) {
        this.objectTag = objectTag == null ? null : objectTag.trim();
    }
}