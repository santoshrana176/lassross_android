package com.mindiii.lasross.module.home.model;

public class AddRemoveWishListResponse {

    /**
     * status : success
     * message : Product successfully added in your wishlist
     * is_wishlist : 1
     */

    private String status;
    private String message;
    private String is_wishlist;

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

    public String getIs_wishlist() {
        return is_wishlist;
    }

    public void setIs_wishlist(String is_wishlist) {
        this.is_wishlist = is_wishlist;
    }
}
