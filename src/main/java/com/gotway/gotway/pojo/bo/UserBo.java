package com.gotway.gotway.pojo.bo;

import java.util.Date;

/**
 * 用于计算 能产生积分的个人资料项
 */
public class UserBo {

    private Date birthday;

    private String phone;

    private String headImg;

    private Integer gender;

    private String area;

    private Double weight;

    private String individualitySign;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getIndividualitySign() {
        return individualitySign;
    }

    public void setIndividualitySign(String individualitySign) {
        this.individualitySign = individualitySign;
    }
}
