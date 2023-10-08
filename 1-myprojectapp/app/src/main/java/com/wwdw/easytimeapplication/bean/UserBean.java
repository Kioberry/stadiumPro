package com.wwdw.easytimeapplication.bean;

import java.io.Serializable;

public class UserBean implements Serializable {
    private String name;
    private String nickname;
    public String id;
    public String password;
    public String userId;
    public String age;
    public String className;
    public String chineseFraction;
    public String time;
    public String address;
    public String userLevelType;
    public String phone;
    public String sex;
    public String esm;
    public String heard;
    public String vip;
    public String vipId;
    public String vipType;
    public String startDate;
    public String endDate;

    public UserBean() {
    }

    public UserBean(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getVipType() {
        return vipType;
    }

    public void setVipType(String vipType) {
        this.vipType = vipType;
    }

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getHeard() {
        return heard;
    }

    public void setHeard(String heard) {
        this.heard = heard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEsm() {
        return esm;
    }

    public void setEsm(String esm) {
        this.esm = esm;
    }

    public String getUserLevelType() {
        return userLevelType;
    }

    public void setUserLevelType(String userLevelType) {
        this.userLevelType = userLevelType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getChineseFraction() {
        return chineseFraction;
    }

    public void setChineseFraction(String chineseFraction) {
        this.chineseFraction = chineseFraction;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
