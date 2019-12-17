package com.gotway.gotway.pojo.vo;

import com.gotway.gotway.pojo.Stick;
import com.gotway.gotway.pojo.User;

import java.util.List;

public class StickVo extends Stick {
    private String userName;
    private String account;
    private String imgs;
    private Integer isReport;
    private Integer isFocus;
    private Integer isPraise;
    private User user;
    private List<CommentsLogVo> commentsLogs;

    public Integer getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(Integer isPraise) {
        this.isPraise = isPraise;
    }

    public Integer getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(Integer isFocus) {
        this.isFocus = isFocus;
    }

    public Integer getIsReport() {
        return isReport;
    }

    public void setIsReport(Integer isReport) {
        this.isReport = isReport;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CommentsLogVo> getCommentsLogs() {
        return commentsLogs;
    }

    public void setCommentsLogs(List<CommentsLogVo> commentsLogs) {
        this.commentsLogs = commentsLogs;
    }
}
