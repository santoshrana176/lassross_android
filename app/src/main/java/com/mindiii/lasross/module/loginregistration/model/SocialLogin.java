package com.mindiii.lasross.module.loginregistration.model;

public class SocialLogin {
    private String cookie;
    private User user;
    private String status;
    private String cookieName;

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    @Override
    public String toString() {
        return
                "SocialLogin{" +
                        "cookie = '" + cookie + '\'' +
                        ",user = '" + user + '\'' +
                        ",status = '" + status + '\'' +
                        ",cookie_name = '" + cookieName + '\'' +
                        "}";
    }
}
