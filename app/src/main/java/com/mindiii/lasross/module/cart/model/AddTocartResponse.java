package com.mindiii.lasross.module.cart.model;

public class AddTocartResponse {

    /**
     * status : success
     * message : Product successfully added in your cart
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
