package com.mindiii.lasross.module.loginregistration.model;

public class User {
    private String firstname;
    private Capabilities capabilities;
    private String registered;
    private String description;
    private String nicename;
    private String avatar;
    private String url;
    private String lastname;
    private String avatarUrlFull;
    private String avatarUrlThumb;
    private String displayname;
    private String nickname;
    private int id;
    private String email;
    private String username;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Capabilities getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Capabilities capabilities) {
        this.capabilities = capabilities;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNicename() {
        return nicename;
    }

    public void setNicename(String nicename) {
        this.nicename = nicename;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAvatarUrlFull() {
        return avatarUrlFull;
    }

    public void setAvatarUrlFull(String avatarUrlFull) {
        this.avatarUrlFull = avatarUrlFull;
    }

    public String getAvatarUrlThumb() {
        return avatarUrlThumb;
    }

    public void setAvatarUrlThumb(String avatarUrlThumb) {
        this.avatarUrlThumb = avatarUrlThumb;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return
                "User{" +
                        "firstname = '" + firstname + '\'' +
                        ",capabilities = '" + capabilities + '\'' +
                        ",registered = '" + registered + '\'' +
                        ",description = '" + description + '\'' +
                        ",nicename = '" + nicename + '\'' +
                        ",avatar = '" + avatar + '\'' +
                        ",url = '" + url + '\'' +
                        ",lastname = '" + lastname + '\'' +
                        ",avatar_url_full = '" + avatarUrlFull + '\'' +
                        ",avatar_url_thumb = '" + avatarUrlThumb + '\'' +
                        ",displayname = '" + displayname + '\'' +
                        ",nickname = '" + nickname + '\'' +
                        ",id = '" + id + '\'' +
                        ",email = '" + email + '\'' +
                        ",username = '" + username + '\'' +
                        "}";
    }
}
