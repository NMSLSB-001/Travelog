package com.example.travelog;

public class selectedItinerary {
    private static String itineraryID, itineraryTitle = "";

    public static String getItineraryID() {
        return itineraryID;
    }

    public static void setItineraryID(String itineraryID) {
        selectedItinerary.itineraryID = itineraryID;
    }

    public static String getItineraryTitle() {
        return itineraryTitle;
    }

    public static void setItineraryTitle(String itineraryTitle) {
        selectedItinerary.itineraryTitle = itineraryTitle;
    }

    public static void resetData() {
        selectedItinerary.itineraryID = "";
        selectedItinerary.itineraryTitle = "";
    }
}
