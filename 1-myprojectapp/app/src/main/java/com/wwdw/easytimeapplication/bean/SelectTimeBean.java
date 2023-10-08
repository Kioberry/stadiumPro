package com.wwdw.easytimeapplication.bean;

import java.io.Serializable;

public class SelectTimeBean implements Serializable {
    private String id;
    private String type;
    private String name;
    private String time;
    private String day;
    private boolean isCheck;
    private String state = "3";//1 已经选过 ，2 正在选中， 3 未选中
    private double money = 10;
    private String location;
    private String isPay;
    private String houesType;
    private String yyTime;
    private int people;
    private int isForm;
    private int isUse;
    private double sum;
    private String appNoId;
    private String userId;
    private String userName;
    private String mark;


    public SelectTimeBean() {
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAppNoId() {
        return appNoId;
    }

    public void setAppNoId(String appNoId) {
        this.appNoId = appNoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYyTime() {
        return yyTime;
    }

    public void setYyTime(String yyTime) {
        this.yyTime = yyTime;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public int getIsForm() {
        return isForm;
    }

    public void setIsForm(int isForm) {
        this.isForm = isForm;
    }

    public String getHouesType() {
        return houesType;
    }

    public void setHouesType(String houesType) {
        this.houesType = houesType;
    }

    public SelectTimeBean(String time) {
        this.time = time;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
