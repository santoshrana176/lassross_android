package com.mindiii.lasross.module.address.model;

import java.util.List;

public class UserAddressListResponse {

    /**
     * status : success
     * message : Found
     * data : {"total_records":"3","user_addresslist":[{"userAddressId":"18","user_id":"224","bill_address_first_name":"test","bill_address_last_name":"","bill_address_company_name":"Home","bill_address_location":"Scheme 54 vijaynagar,Near by krishna dairy","bill_address_latitude":"22.751752","bill_address_longitude":"75.897010","bill_address_country":"","bill_address_house_number":"","bill_address_locality":"","bill_address_city":"","bill_address_phone":"9756466807","bill_address_zip_code":"","bill_address_email":"test@g.com","ship_address_first_name":"test","ship_address_last_name":"","ship_address_company_name":"Home","ship_address_location":"Scheme 54 vijaynagar,Near by krishna dairy","ship_address_latitude":"22.751752","ship_address_longitude":"75.897010","ship_address_country":"","ship_address_house_number":"","ship_address_locality":"","ship_address_city":"","ship_address_zip_code":"","updated_on":"2019-10-09 09:32:00","created_on":"2019-10-09 09:32:00"},{"userAddressId":"22","user_id":"224","bill_address_first_name":"test","bill_address_last_name":"","bill_address_company_name":"Home","bill_address_location":"Scheme 54 vijaynagar,Near by krishna dairy","bill_address_latitude":"22.751752","bill_address_longitude":"75.897010","bill_address_country":"","bill_address_house_number":"","bill_address_locality":"","bill_address_city":"","bill_address_phone":"9756466807","bill_address_zip_code":"","bill_address_email":"test@g.com","ship_address_first_name":"test","ship_address_last_name":"","ship_address_company_name":"Home","ship_address_location":"Scheme 54 vijaynagar,Near by krishna dairy","ship_address_latitude":"22.751752","ship_address_longitude":"75.897010","ship_address_country":"","ship_address_house_number":"","ship_address_locality":"","ship_address_city":"","ship_address_zip_code":"","updated_on":"2019-10-09 12:29:55","created_on":"2019-10-09 12:29:55"},{"userAddressId":"23","user_id":"224","bill_address_first_name":"test","bill_address_last_name":"","bill_address_company_name":"Home","bill_address_location":"Scheme 54 vijaynagar,Near by krishna dairy","bill_address_latitude":"22.751752","bill_address_longitude":"75.897010","bill_address_country":"","bill_address_house_number":"","bill_address_locality":"","bill_address_city":"","bill_address_phone":"9756466807","bill_address_zip_code":"","bill_address_email":"test@g.com","ship_address_first_name":"test","ship_address_last_name":"","ship_address_company_name":"Home","ship_address_location":"Scheme 54 vijaynagar,Near by krishna dairy","ship_address_latitude":"22.751752","ship_address_longitude":"75.897010","ship_address_country":"","ship_address_house_number":"","ship_address_locality":"","ship_address_city":"","ship_address_zip_code":"","updated_on":"2019-10-09 12:30:29","created_on":"2019-10-09 12:30:29"}]}
     */

    private String status;
    private String message;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String total_records;
        private List<UserAddresslistBean> user_addresslist;

        public String getTotal_records() {
            return total_records;
        }

        public void setTotal_records(String total_records) {
            this.total_records = total_records;
        }

        public List<UserAddresslistBean> getUser_addresslist() {
            return user_addresslist;
        }

        public void setUser_addresslist(List<UserAddresslistBean> user_addresslist) {
            this.user_addresslist = user_addresslist;
        }

        public static class UserAddresslistBean {

            private String userAddressId;
            private String user_id;
            private String bill_address_first_name;
            private String bill_address_last_name;
            private String bill_address_company_name;
            private String bill_address_location;
            private String bill_address_latitude;
            private String bill_address_longitude;
            private String bill_address_country;
            private String bill_address_house_number;
            private String bill_address_locality;
            private String bill_address_city;
            private String bill_address_phone;
            private String bill_address_zip_code;
            private String bill_address_email;
            private String ship_address_first_name;
            private String ship_address_last_name;
            private String ship_address_company_name;
            private String ship_address_location;
            private String ship_address_latitude;
            private String ship_address_longitude;
            private String ship_address_country;
            private String ship_address_house_number;
            private String ship_address_locality;
            private String ship_address_city;
            private String ship_address_zip_code;
            private String updated_on;
            private String created_on;

            public String getUserAddressId() {
                return userAddressId;
            }

            public void setUserAddressId(String userAddressId) {
                this.userAddressId = userAddressId;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getBill_address_first_name() {
                return bill_address_first_name;
            }

            public void setBill_address_first_name(String bill_address_first_name) {
                this.bill_address_first_name = bill_address_first_name;
            }

            public String getBill_address_last_name() {
                return bill_address_last_name;
            }

            public void setBill_address_last_name(String bill_address_last_name) {
                this.bill_address_last_name = bill_address_last_name;
            }

            public String getBill_address_company_name() {
                return bill_address_company_name;
            }

            public void setBill_address_company_name(String bill_address_company_name) {
                this.bill_address_company_name = bill_address_company_name;
            }

            public String getBill_address_location() {
                return bill_address_location;
            }

            public void setBill_address_location(String bill_address_location) {
                this.bill_address_location = bill_address_location;
            }

            public String getBill_address_latitude() {
                return bill_address_latitude;
            }

            public void setBill_address_latitude(String bill_address_latitude) {
                this.bill_address_latitude = bill_address_latitude;
            }

            public String getBill_address_longitude() {
                return bill_address_longitude;
            }

            public void setBill_address_longitude(String bill_address_longitude) {
                this.bill_address_longitude = bill_address_longitude;
            }

            public String getBill_address_country() {
                return bill_address_country;
            }

            public void setBill_address_country(String bill_address_country) {
                this.bill_address_country = bill_address_country;
            }

            public String getBill_address_house_number() {
                return bill_address_house_number;
            }

            public void setBill_address_house_number(String bill_address_house_number) {
                this.bill_address_house_number = bill_address_house_number;
            }

            public String getBill_address_locality() {
                return bill_address_locality;
            }

            public void setBill_address_locality(String bill_address_locality) {
                this.bill_address_locality = bill_address_locality;
            }

            public String getBill_address_city() {
                return bill_address_city;
            }

            public void setBill_address_city(String bill_address_city) {
                this.bill_address_city = bill_address_city;
            }

            public String getBill_address_phone() {
                return bill_address_phone;
            }

            public void setBill_address_phone(String bill_address_phone) {
                this.bill_address_phone = bill_address_phone;
            }

            public String getBill_address_zip_code() {
                return bill_address_zip_code;
            }

            public void setBill_address_zip_code(String bill_address_zip_code) {
                this.bill_address_zip_code = bill_address_zip_code;
            }

            public String getBill_address_email() {
                return bill_address_email;
            }

            public void setBill_address_email(String bill_address_email) {
                this.bill_address_email = bill_address_email;
            }

            public String getShip_address_first_name() {
                return ship_address_first_name;
            }

            public void setShip_address_first_name(String ship_address_first_name) {
                this.ship_address_first_name = ship_address_first_name;
            }

            public String getShip_address_last_name() {
                return ship_address_last_name;
            }

            public void setShip_address_last_name(String ship_address_last_name) {
                this.ship_address_last_name = ship_address_last_name;
            }

            public String getShip_address_company_name() {
                return ship_address_company_name;
            }

            public void setShip_address_company_name(String ship_address_company_name) {
                this.ship_address_company_name = ship_address_company_name;
            }

            public String getShip_address_location() {
                return ship_address_location;
            }

            public void setShip_address_location(String ship_address_location) {
                this.ship_address_location = ship_address_location;
            }

            public String getShip_address_latitude() {
                return ship_address_latitude;
            }

            public void setShip_address_latitude(String ship_address_latitude) {
                this.ship_address_latitude = ship_address_latitude;
            }

            public String getShip_address_longitude() {
                return ship_address_longitude;
            }

            public void setShip_address_longitude(String ship_address_longitude) {
                this.ship_address_longitude = ship_address_longitude;
            }

            public String getShip_address_country() {
                return ship_address_country;
            }

            public void setShip_address_country(String ship_address_country) {
                this.ship_address_country = ship_address_country;
            }

            public String getShip_address_house_number() {
                return ship_address_house_number;
            }

            public void setShip_address_house_number(String ship_address_house_number) {
                this.ship_address_house_number = ship_address_house_number;
            }

            public String getShip_address_locality() {
                return ship_address_locality;
            }

            public void setShip_address_locality(String ship_address_locality) {
                this.ship_address_locality = ship_address_locality;
            }

            public String getShip_address_city() {
                return ship_address_city;
            }

            public void setShip_address_city(String ship_address_city) {
                this.ship_address_city = ship_address_city;
            }

            public String getShip_address_zip_code() {
                return ship_address_zip_code;
            }

            public void setShip_address_zip_code(String ship_address_zip_code) {
                this.ship_address_zip_code = ship_address_zip_code;
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
        }
    }
}
