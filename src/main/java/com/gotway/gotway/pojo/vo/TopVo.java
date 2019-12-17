package com.gotway.gotway.pojo.vo;

import com.gotway.gotway.pojo.User;

public class TopVo {

    private User user;
    private Integer ranking=-1;//默认-1 未上榜
    private Double dataVal=0D;//默认 0

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Double getDataVal() {
        return dataVal;
    }

    public void setDataVal(Double dataVal) {
        this.dataVal = dataVal;
    }
}
