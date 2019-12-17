package com.gotway.gotway.pojo.vo;

import com.gotway.gotway.pojo.Stick;
import com.gotway.gotway.pojo.User;

import java.util.List;

public class StickVoForPraise extends Stick {
    private String imgs;
    private User user;
    private List<UserVoForApp> praiseUsers;
    private boolean isMyPraise=false;//我的消息-》我的点赞，用于区分是否是我点赞过的帖子，true ,表示是。

    public boolean isMyPraise() {
        return isMyPraise;
    }

    public void setMyPraise(boolean myPraise) {
        isMyPraise = myPraise;
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

    public List<UserVoForApp> getPraiseUsers() {
        return praiseUsers;
    }

    public void setPraiseUsers(List<UserVoForApp> praiseUsers) {
        this.praiseUsers = praiseUsers;
    }
}
