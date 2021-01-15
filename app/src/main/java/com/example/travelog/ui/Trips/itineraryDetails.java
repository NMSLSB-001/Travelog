package com.example.travelog.ui.Trips;

public class itineraryDetails {
    String startTime, description, title, location;

    public itineraryDetails(String startTime, String description, String title, String location) {
        this.startTime = startTime;
        this.description = description;
        this.title = title;
        this.location = location;
    }

    public String getTime() {
        return startTime;
    }

    public void setTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
