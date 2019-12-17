package com.gotway.gotway.pojo.vo;

import com.gotway.gotway.pojo.User;

public class UserVoNearBy extends User {
    private Double distance;
    private Integer isFocus;

    public Integer getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(Integer isFocus) {
        this.isFocus = isFocus;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
