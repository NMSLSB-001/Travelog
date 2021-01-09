package com.example.travelog.ui.Trips;


import androidx.room.PrimaryKey;

import java.util.Comparator;

public class ItineraryRow {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String rowTitle, description, location ,startTime, endTime;
    private Boolean expandable;

    public ItineraryRow(String rowTitle, String description, String location, String startTime, String endTime)
    {
        this.rowTitle = rowTitle;
        this.description = description;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public ItineraryRow(Boolean expandable) {
        this.expandable = expandable;
    }

    public String getRowTitle() {
        return rowTitle;
    }

    public void setRowTitle(String rowTitle) {
        this.rowTitle = rowTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "ItineraryRow{" +
                "rowTitle='" + rowTitle + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }


    public static final Comparator<ItineraryRow> AscendingHour = new Comparator<ItineraryRow>() {
        @Override
        public int compare(ItineraryRow o1, ItineraryRow o2) {
            return o1.getStartTime().compareTo(o2.getStartTime());
        }
    };


}
