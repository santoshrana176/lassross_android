
package com.mindiii.lasross.base.errorResponse;

@SuppressWarnings("unused")
public class APIErrors {

    /**
     * message : Invalid token
     * authToken :
     * responseCode : 300
     * isActive : 1
     */

    private String message;
    private String authToken;
    private int responseCode;
    private int isActive;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
