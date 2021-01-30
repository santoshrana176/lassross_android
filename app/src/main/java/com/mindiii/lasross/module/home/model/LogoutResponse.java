package com.mindiii.lasross.module.home.model;

public class LogoutResponse {

    /**
     * status : success
     * message : Logged out successfully
     */

    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
