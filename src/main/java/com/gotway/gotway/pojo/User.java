package com.gotway.gotway.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gotway.gotway.common.pojo.CustomDateSerializer;

import java.util.Date;

public class User {
    private Integer id;

    private String account;

    private String nickname;

    private String realName;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date birthday;

    private String phone;

    private Integer phoneTag;

    private String email;

    private Integer emailTag;
    @JsonIgnore
    private String password;

    private String headImg;

    private Integer gender;

    private String area;

    private Double weight;

    private String individualitySign;

    private Integer userType;

    private Integer roleTag;

    private Date createTime;

    private Integer state;

    private String systemModel;

    private Integer integralLevel;

    private Integer integralQty;

    private Double mileageTotal;

    private Double longitude;

    private Double latitude;

    private Integer locationTag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

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
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getPhoneTag() {
        return phoneTag;
    }

    public void setPhoneTag(Integer phoneTag) {
        this.phoneTag = phoneTag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getEmailTag() {
        return emailTag;
    }

    public void setEmailTag(Integer emailTag) {
        this.emailTag = emailTag;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
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
        this.area = area == null ? null : area.trim();
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
        this.individualitySign = individualitySign == null ? null : individualitySign.trim();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getRoleTag() {
        return roleTag;
    }

    public void setRoleTag(Integer roleTag) {
        this.roleTag = roleTag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSystemModel() {
        return systemModel;
    }

    public void setSystemModel(String systemModel) {
        this.systemModel = systemModel == null ? null : systemModel.trim();
    }

    public Integer getIntegralLevel() {
        return integralLevel;
    }

    public void setIntegralLevel(Integer integralLevel) {
        this.integralLevel = integralLevel;
    }

    public Integer getIntegralQty() {
        return integralQty;
    }

    public void setIntegralQty(Integer integralQty) {
        this.integralQty = integralQty;
    }

    public Double getMileageTotal() {
        return mileageTotal;
    }

    public void setMileageTotal(Double mileageTotal) {
        this.mileageTotal = mileageTotal;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getLocationTag() {
        return locationTag;
    }

    public void setLocationTag(Integer locationTag) {
        this.locationTag = locationTag;
    }
}