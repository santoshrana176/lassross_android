package com.mindiii.lasross.module.loginregistration.model;

public class ForgotPasswordResponse {


    /*{
        "status" : "success"
        "message" : "Your password has been sent to your email"
     }*/


    /*{
        "status": "fail",
        "message": "Invalid Email"
    }*/

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
