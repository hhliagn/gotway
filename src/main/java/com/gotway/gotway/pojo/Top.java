package com.gotway.gotway.pojo;

import java.util.Date;

public class Top {
    private Integer id;

    private Integer memId;

    private Double sumMileage;

    private Double todayMileage;

    private Double weekMileage;

    private Double monthMileage;

    private Integer supportCount;

    private Integer topicCount;

    private Integer level;

    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public Double getSumMileage() {
        return sumMileage;
    }

    public void setSumMileage(Double sumMileage) {
        this.sumMileage = sumMileage;
    }

    public Double getTodayMileage() {
        return todayMileage;
    }

    public void setTodayMileage(Double todayMileage) {
        this.todayMileage = todayMileage;
    }

    public Double getWeekMileage() {
        return weekMileage;
    }

    public void setWeekMileage(Double weekMileage) {
        this.weekMileage = weekMileage;
    }

    public Double getMonthMileage() {
        return monthMileage;
    }

    public void setMonthMileage(Double monthMileage) {
        this.monthMileage = monthMileage;
    }

    public Integer getSupportCount() {
        return supportCount;
    }

    public void setSupportCount(Integer supportCount) {
        this.supportCount = supportCount;
    }

    public Integer getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(Integer topicCount) {
        this.topicCount = topicCount;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}