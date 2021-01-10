package com.example.travelog.ui.Trips;


import androidx.room.PrimaryKey;

import java.util.Comparator;

public class ItineraryRowHour {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String rowTitle, description, location ,startTime;
    private Boolean expandable;

    public ItineraryRowHour(String rowTitle, String description, String location, String startTime)
    {
        this.rowTitle = rowTitle;
        this.description = description;
        this.location = location;
        this.startTime = startTime;
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

    public ItineraryRowHour(Boolean expandable) {
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


    @Override
    public String toString() {
        return "ItineraryRow{" +
                "rowTitle='" + rowTitle + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", startTime=" + startTime +
                '}';
    }


    //reorder to ascending order function
    public static final Comparator<ItineraryRowHour> AscendingHour = new Comparator<ItineraryRowHour>() {
        @Override
        public int compare(ItineraryRowHour o1, ItineraryRowHour o2) {
            return o1.getStartTime().compareTo(o2.getStartTime());
        }
    };


}
