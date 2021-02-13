
package com.mindiii.lasross.module.loginregistration.model;

public class LoginResponse {

    /*{
    "status": "fail",
    "message": "Your account is already registered and is not associated with Social Account. Please try logging in without using Social account.",
    "messageCode": "social_reg",
    "data": {
        "userDetail": {}
    }
    }*/

    /**
     * status : success
     * message : User registration successfully done
     * messageCode : social_reg
     * data : {"userDetail":{"userId":"186","first_name":"Jyoti","last_name":"Singh","full_name":"Jyoti Singh","email":"ghkjhjk@gmail.com","auth_token":"37686351ad91ecfae64bccadf10fef8668d347fd","status":"1","updated_on":"2019-09-19 11:24:56","created_on":"2019-09-19 11:24:56","profile_photo":"","device_type":"1","device_token":"","signup_from":"1","social_type":"facebook","social_id":"4hhhhhh7687","thumbImage":"http://dev.api.lasross.com/uploads/placeholders/user_placeholder.png"}}
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

        private UserDetailBean userDetail;

        public UserDetailBean getUserDetail() {
            return userDetail;
        }

        public void setUserDetail(UserDetailBean userDetail) {
            this.userDetail = userDetail;
        }

        public static class UserDetailBean {
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
            private String social_type;
            private String social_id;
            private String stripe_customer_id;

            public String getPush_alert_status() {
                return push_alert_status;
            }

            public void setPush_alert_status(String push_alert_status) {
                this.push_alert_status = push_alert_status;
            }

            private String push_alert_status;

            //private SubscriptionDetailBean subscription_detail;


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

            public String getSocial_type() {
                return social_type;
            }

            public void setSocial_type(String social_type) {
                this.social_type = social_type;
            }

            public String getSocial_id() {
                return social_id;
            }

            public void setSocial_id(String social_id) {
                this.social_id = social_id;
            }

            public String getStripe_customer_id() {
                return stripe_customer_id;
            }

            public void setStripe_customer_id(String stripe_customer_id) {
                this.stripe_customer_id = stripe_customer_id;
            }
        }
    }
}
