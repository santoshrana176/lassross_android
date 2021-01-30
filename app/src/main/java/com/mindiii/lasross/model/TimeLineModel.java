package com.mindiii.lasross.model;

public class TimeLineModel {

    String message, date, status;

    public TimeLineModel(String message, String date, String status) {
        this.message = message;
        this.date = date;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
