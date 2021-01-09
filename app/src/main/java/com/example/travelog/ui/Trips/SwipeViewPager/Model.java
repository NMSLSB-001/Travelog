package com.example.travelog.ui.Trips.SwipeViewPager;

public class Model {

    private int image;
    private String title;
    private String date;

    public Model(int image, String title, String desc) {
        this.image = image;
        this.title = title;
        this.date = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return date;
    }

    public void setDesc(String date) {
        this.date = date;
    }
}
