package com.gotway.gotway.pojo.vo;

import com.gotway.gotway.pojo.User;

public class UserVo2 extends User {
    private Integer roleId;
    private String roleName;
    private Integer topicCount;

    public Integer getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(Integer topicCount) {
        this.topicCount = topicCount;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
