package com.gotway.gotway.pojo.vo;

import com.gotway.gotway.pojo.SysLog;

public class SysLogVo extends SysLog {
    private String userName;
    private String account;

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
}
