package com.example.travelog.ui.Trips;


import androidx.room.PrimaryKey;

import java.util.Comparator;

public class ItineraryRowDay {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String dayTitle, description, dayNum;
    private Boolean expandable;

    public ItineraryRowDay(String dayTitle, String description, String dayNum)
    {
        this.dayTitle = dayTitle;
        this.description = description;
        this.dayNum = dayNum;
        this.expandable = false;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public Boolean getExpandable() {
        return expandable;
    }

    public void setExpandable(Boolean expandable) {
        this.expandable = expandable;
    }

    public ItineraryRowDay(Boolean expandable) {
        this.expandable = expandable;
    }

    public String getDayTitle() {
        return dayTitle;
    }

    public void setDayTitle(String dayTitle) {
        this.dayTitle = dayTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDayNum() {
        return dayNum;
    }

    public void setDayNum(String dayNum) {
        this.dayNum = dayNum;
    }


    @Override
    public String toString() {
        return "ItineraryRow{" +
                "dayTitle='" + dayTitle + '\'' +
                ", description='" + description + '\'' +
                ", dayNum=" + dayNum +
                '}';
    }



}
