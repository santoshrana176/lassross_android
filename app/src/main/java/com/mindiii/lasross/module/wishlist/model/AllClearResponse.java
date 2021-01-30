package com.mindiii.lasross.module.wishlist.model;

public class AllClearResponse {

    /**
     * status : success
     * message : All Products successfully removed from your wishlist.
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
