package com.example.travelog.ui.Trips;

public class firebase_itinerary {
    public String getItinerary_title() {
        return itinerary_title;
    }

    public void setItinerary_title(String itinerary_title) {
        this.itinerary_title = itinerary_title;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    String itinerary_title, startDate, endDate, location, itineraryID;

    public String getItineraryID() {
        return itineraryID;
    }

    public void setItineraryID(String itineraryID) {
        this.itineraryID = itineraryID;
    }

    public firebase_itinerary(String itinerary_title, String startDate, String endDate, String location, String itineraryID) {
        this.itinerary_title = itinerary_title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.itineraryID = itineraryID;
    }
}
