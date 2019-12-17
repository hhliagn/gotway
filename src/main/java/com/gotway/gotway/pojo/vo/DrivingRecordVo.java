package com.gotway.gotway.pojo.vo;

import com.gotway.gotway.pojo.DrivingRecord;

public class DrivingRecordVo extends DrivingRecord {
    private String userName;
    private String account;
    private Integer gender;
    private Double mileageTotal;

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Double getMileageTotal() {
        return mileageTotal;
    }

    public void setMileageTotal(Double mileageTotal) {
        this.mileageTotal = mileageTotal;
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
}
