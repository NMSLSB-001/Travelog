package com.example.travelog.ui.Trips.SwipeViewPager;

public class Model {

    private int image;
    private String title, startDate, endDate, loc;

    public Model(int image, String title, String startDate, String endDate, String loc) {
        this.image = image;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.loc = loc;
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

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
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
}
