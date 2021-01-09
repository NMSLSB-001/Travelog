package com.example.travelog.ui.DiscoverFragment.View.BeanModel;

import java.util.List;

/**
 * Created by dong.he on 2017/2/6.
 */

public class ListItemModel {

    private int id;
    private String userName;
    private String headImg;
    private String location;
    private String title;
    private String content;
    private String time;
//  public String[] urls;
    public List<String> urls;
    //
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    //
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    //
    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
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
    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }


    @Override
    public String toString() {
        return "ListItemModle{" +
                "id=" + id +
                ", name='" + userName + '\'' +
                ", headImg='" + headImg + '\'' +
                ", location='"+ location + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", urls=" + urls +
                '}';
    }
}
