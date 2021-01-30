package com.mindiii.lasross.module.loginregistration.model;

public class SocialSignUpModel {


    /**
     * status : success
     * message : User authentication successfully done
     * messageCode : social_login
     * data : {"userDetail":{"userId":"46","first_name":"Anil","last_name":"Singh","full_name":"Anil Singh","email":"ranu.mindiii@gmail.com","auth_token":"499aa8c00bca054ac4e2acac625257a94d5d292e","status":"1","updated_on":"2019-09-14 11:05:46","created_on":"2019-09-14 11:05:46","profile_photo":"","device_type":"1","device_token":"","signup_from":"1","thumbImage":"http://18.222.186.13/uploads/placeholders/user_placeholder.png"}}
     */

    private String status;
    private String message;
    private String messageCode;
    private DataBean data;

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

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userDetail : {"userId":"46","first_name":"Anil","last_name":"Singh","full_name":"Anil Singh","email":"ranu.mindiii@gmail.com","auth_token":"499aa8c00bca054ac4e2acac625257a94d5d292e","status":"1","updated_on":"2019-09-14 11:05:46","created_on":"2019-09-14 11:05:46","profile_photo":"","device_type":"1","device_token":"","signup_from":"1","thumbImage":"http://18.222.186.13/uploads/placeholders/user_placeholder.png"}
         */

        private UserDetailBean userDetail;

        public UserDetailBean getUserDetail() {
            return userDetail;
        }

        public void setUserDetail(UserDetailBean userDetail) {
            this.userDetail = userDetail;
        }

        public static class UserDetailBean {
            /**
             * userId : 46
             * first_name : Anil
             * last_name : Singh
             * full_name : Anil Singh
             * email : ranu.mindiii@gmail.com
             * auth_token : 499aa8c00bca054ac4e2acac625257a94d5d292e
             * status : 1
             * updated_on : 2019-09-14 11:05:46
             * created_on : 2019-09-14 11:05:46
             * profile_photo :
             * device_type : 1
             * device_token :
             * signup_from : 1
             * thumbImage : http://18.222.186.13/uploads/placeholders/user_placeholder.png
             */

            private String userId;
            private String first_name;
            private String last_name;
            private String full_name;
            private String email;
            private String auth_token;
            private String status;
            private String updated_on;
            private String created_on;
            private String profile_photo;
            private String device_type;
            private String device_token;
            private String signup_from;
            private String thumbImage;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
            }

            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;
            }

            public String getFull_name() {
                return full_name;
            }

            public void setFull_name(String full_name) {
                this.full_name = full_name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getAuth_token() {
                return auth_token;
            }

            public void setAuth_token(String auth_token) {
                this.auth_token = auth_token;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getUpdated_on() {
                return updated_on;
            }

            public void setUpdated_on(String updated_on) {
                this.updated_on = updated_on;
            }

            public String getCreated_on() {
                return created_on;
            }

            public void setCreated_on(String created_on) {
                this.created_on = created_on;
            }

            public String getProfile_photo() {
                return profile_photo;
            }

            public void setProfile_photo(String profile_photo) {
                this.profile_photo = profile_photo;
            }

            public String getDevice_type() {
                return device_type;
            }

            public void setDevice_type(String device_type) {
                this.device_type = device_type;
            }

            public String getDevice_token() {
                return device_token;
            }

            public void setDevice_token(String device_token) {
                this.device_token = device_token;
            }

            public String getSignup_from() {
                return signup_from;
            }

            public void setSignup_from(String signup_from) {
                this.signup_from = signup_from;
            }

            public String getThumbImage() {
                return thumbImage;
            }

            public void setThumbImage(String thumbImage) {
                this.thumbImage = thumbImage;
            }
        }
    }
}
