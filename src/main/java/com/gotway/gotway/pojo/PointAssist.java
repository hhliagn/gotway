package com.gotway.gotway.pojo;

import java.util.Date;

public class PointAssist {
    private Integer id;

    private Integer stickId;

    private Integer userId;

    private Integer num;

    private Date createDate;

    private Integer drivingRecordId;

    private Double mileage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStickId() {
        return stickId;
    }

    public void setStickId(Integer stickId) {
        this.stickId = stickId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getDrivingRecordId() {
        return drivingRecordId;
    }

    public void setDrivingRecordId(Integer drivingRecordId) {
        this.drivingRecordId = drivingRecordId;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }
}