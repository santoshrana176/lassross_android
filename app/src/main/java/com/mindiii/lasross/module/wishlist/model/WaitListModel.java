package com.mindiii.lasross.module.wishlist.model;

import java.util.List;

public class WaitListModel {

    /**
     * status : success
     * message : Found
     * data : {"user_wishlist":[{"productId":"30","product_name":"Denim Jacket","product_sku":"DJ-50","regular_price":"400","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"0","collection_type_id":"1","in_stock":"1","status":"1","updated_on":"2019-09-20 14:31:32","created_on":"2019-09-20 14:31:32","category_id":"1, 5","category_name":"Men, T-shirt","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/KgJN675EdYhUiDoR.jpg"},{"productId":"35","product_name":"shirt2","product_sku":"ss-22","regular_price":"322","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"232","collection_type_id":"3","in_stock":"1","status":"1","updated_on":"2019-09-23 13:01:50","created_on":"2019-09-23 13:01:50","category_id":"3, 17","category_name":"Girls, Shoes","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/aYg6C3NFizPG4eKo.jpg"},{"productId":"38","product_name":"Hoddie7","product_sku":"ss-22","regular_price":"4545","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"5445","collection_type_id":"1","in_stock":"1","status":"1","updated_on":"2019-09-23 13:04:09","created_on":"2019-09-23 13:04:09","category_id":"8, 16","category_name":"Kids , t-shirts","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/4FbhI17x96YuX3iW.jpg"},{"productId":"39","product_name":"Hoddie4","product_sku":"fgf","regular_price":"445","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"455","collection_type_id":"3","in_stock":"1","status":"1","updated_on":"2019-09-23 13:04:48","created_on":"2019-09-23 13:04:48","category_id":"3, 20","category_name":"Girls, Girls shirts","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/WOND5K6sd7lTqnJh.jpg"},{"productId":"41","product_name":"frock2","product_sku":"ss-22","regular_price":"3434","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"343","collection_type_id":"2","in_stock":"1","status":"1","updated_on":"2019-09-23 13:12:10","created_on":"2019-09-23 13:12:10","category_id":"2, 21","category_name":"Women, Women shirts","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/842Dq9yaWr6uOlGd.jpg"},{"productId":"42","product_name":"shirt8","product_sku":"ss-22","regular_price":"34","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"34","collection_type_id":"3","in_stock":"1","status":"1","updated_on":"2019-09-23 13:16:16","created_on":"2019-09-23 13:16:16","category_id":"1, 4, 5","category_name":"Men, Shirt, T-shirt","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/ZcVO4XrRvJgeo2iA.jpg"}]}
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
        private List<UserWishlistBean> user_wishlist;

        public List<UserWishlistBean> getUser_wishlist() {
            return user_wishlist;
        }

        public void setUser_wishlist(List<UserWishlistBean> user_wishlist) {
            this.user_wishlist = user_wishlist;
        }

        public static class UserWishlistBean {
            /**
             * productId : 30
             * product_name : Denim Jacket
             * product_sku : DJ-50
             * regular_price : 400
             * currency_code : Euro
             * currency_symbol : €
             * sale_price : 0
             * collection_type_id : 1
             * in_stock : 1
             * status : 1
             * updated_on : 2019-09-20 14:31:32
             * created_on : 2019-09-20 14:31:32
             * category_id : 1, 5
             * category_name : Men, T-shirt
             * product_image : https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/KgJN675EdYhUiDoR.jpg
             */

            private String productId;
            private String product_name;
            private String product_sku;
            private String regular_price;
            private String currency_code;
            private String currency_symbol;
            private String sale_price;
            private String collection_type_id;
            private String in_stock;
            private String status;
            private String updated_on;
            private String created_on;
            private String category_id;
            private String category_name;
            private String product_image;

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public String getProduct_sku() {
                return product_sku;
            }

            public void setProduct_sku(String product_sku) {
                this.product_sku = product_sku;
            }

            public String getRegular_price() {
                return regular_price;
            }

            public void setRegular_price(String regular_price) {
                this.regular_price = regular_price;
            }

            public String getCurrency_code() {
                return currency_code;
            }

            public void setCurrency_code(String currency_code) {
                this.currency_code = currency_code;
            }

            public String getCurrency_symbol() {
                return currency_symbol;
            }

            public void setCurrency_symbol(String currency_symbol) {
                this.currency_symbol = currency_symbol;
            }

            public String getSale_price() {
                return sale_price;
            }

            public void setSale_price(String sale_price) {
                this.sale_price = sale_price;
            }

            public String getCollection_type_id() {
                return collection_type_id;
            }

            public void setCollection_type_id(String collection_type_id) {
                this.collection_type_id = collection_type_id;
            }

            public String getIn_stock() {
                return in_stock;
            }

            public void setIn_stock(String in_stock) {
                this.in_stock = in_stock;
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

            public String getCategory_id() {
                return category_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }

            public String getCategory_name() {
                return category_name;
            }

            public void setCategory_name(String category_name) {
                this.category_name = category_name;
            }

            public String getProduct_image() {
                return product_image;
            }

            public void setProduct_image(String product_image) {
                this.product_image = product_image;
            }
        }
    }
}
