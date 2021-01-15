package com.example.travelog.ui.DiscoverFragment.View.BeanModel;

public class GetArticle {
    private int id;
    private String userId;
    private String location;
    private String title;
    private String content;
    private String time;
    private String urlJson;

    public GetArticle() {

    }
    public GetArticle(int id, String userId, String location, String title, String content, String time, String urlJson) {
        this.id = id;
        this.userId = userId;
        this.location = location;
        this.title = title;
        this.content = content;
        this.time = time;
        this.urlJson = urlJson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    //
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    //
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    //
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    //
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    //
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    //
    public String getUrlJson() {
        return urlJson;
    }

    public void setUrlJson(String urlJson) {
        this.urlJson = urlJson;
    }
}
