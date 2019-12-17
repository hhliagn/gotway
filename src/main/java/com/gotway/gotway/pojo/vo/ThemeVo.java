package com.gotway.gotway.pojo.vo;

import com.gotway.gotway.pojo.Theme;

public class ThemeVo extends Theme {
    private String userName;
    private String account;
    private String imgs;
    private Integer stickNum;//参与帖子数

    public Integer getStickNum() {
        return stickNum;
    }

    public void setStickNum(Integer stickNum) {
        this.stickNum = stickNum;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
