package com.gotway.gotway.pojo.vo;

import com.gotway.gotway.pojo.Role;

public class RoleVo extends Role {
    private Integer userCount;

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }
}
