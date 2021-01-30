package com.mindiii.lasross.model;

public class NotificationModel {

    String ItemHeading;
    String ItemDetails;
    String Time;

    public NotificationModel(String itemHeading, String itemDetails, String time) {
        ItemHeading = itemHeading;
        ItemDetails = itemDetails;
        Time = time;
    }

    public String getItemHeading() {
        return ItemHeading;
    }

    public String getItemDetails() {
        return ItemDetails;
    }

    public String getTime() {
        return Time;
    }
}
