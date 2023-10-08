package com.wwdw.easytimeapplication.bean;


import java.io.Serializable;

public class NewsListBean implements Serializable {
    private String title;
    private String msg;
    private String time;
    private int url;
    private String urlxx;
    private String type = "1";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public String getUrlxx() {
        return urlxx;
    }

    public void setUrlxx(String urlxx) {
        this.urlxx = urlxx;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
