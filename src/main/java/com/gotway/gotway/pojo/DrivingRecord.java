package com.gotway.gotway.pojo;

import java.util.Date;

public class DrivingRecord {
    private Integer id;

    private Integer userId;

    private String deviceName;

    private String deviceMac;

    private Date createTime;

    private Double mileage;

    private Long duration;

    private Integer electricity;

    private Double highestSpeed;

    private Double aveSpeed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac == null ? null : deviceMac.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getElectricity() {
        return electricity;
    }

    public void setElectricity(Integer electricity) {
        this.electricity = electricity;
    }

    public Double getHighestSpeed() {
        return highestSpeed;
    }

    public void setHighestSpeed(Double highestSpeed) {
        this.highestSpeed = highestSpeed;
    }

    public Double getAveSpeed() {
        return aveSpeed;
    }

    public void setAveSpeed(Double aveSpeed) {
        this.aveSpeed = aveSpeed;
    }
}