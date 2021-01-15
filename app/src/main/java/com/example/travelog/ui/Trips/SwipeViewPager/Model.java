package com.example.travelog.ui.Trips.SwipeViewPager;

public class Model {

    private int image;
    private String title;
    private String itineraryID;

    public String getItineraryID() {
        return itineraryID;
    }

    public void setItineraryID(String itineraryID) {
        this.itineraryID = itineraryID;
    }

    public Model(int image, String title, String itineraryID) {
        this.image = image;
        this.title = title;
        this.itineraryID = itineraryID;
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

}
