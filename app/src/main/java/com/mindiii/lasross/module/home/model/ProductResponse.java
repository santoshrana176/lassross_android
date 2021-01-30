package com.mindiii.lasross.module.home.model;

import java.util.List;

public class ProductResponse {

    /**
     * status : success
     * message : Found
     * maxPrice : 5000
     * minPrice : 30
     * data : {"total_records":"33","product_list":[
     * {"productId":"42","product_name":"shirt8","product_sku":"ss-22","regular_price":"34","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"34","collection_type_id":"3","in_stock":"1","status":"1","updated_on":"2019-09-23 13:16:16","created_on":"2019-09-23 13:16:16","category_id":"1, 4, 5","category_name":"Men, Shirt, T-shirt","variant_id":"5, 2","variant_value":"black, medium","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/ZcVO4XrRvJgeo2iA.jpg"},{"productId":"41","product_name":"frock2","product_sku":"ss-22","regular_price":"3434","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"343","collection_type_id":"2","in_stock":"1","status":"1","updated_on":"2019-09-23 13:12:10","created_on":"2019-09-23 13:12:10","category_id":"21, 2","category_name":"Women shirts, Women","variant_id":"3, 6","variant_value":"large, white","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/842Dq9yaWr6uOlGd.jpg"},{"productId":"40","product_name":"shirt7","product_sku":"ss-22","regular_price":"233","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"23","collection_type_id":"2","in_stock":"1","status":"1","updated_on":"2019-09-23 13:05:36","created_on":"2019-09-23 13:05:36","category_id":"12, 2","category_name":"Jeans, Women","variant_id":"3, 6","variant_value":"large, white","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/fIjaVbuD4l8SnLqz.jpg"},{"productId":"39","product_name":"Hoddie4","product_sku":"fgf","regular_price":"445","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"455","collection_type_id":"3","in_stock":"1","status":"1","updated_on":"2019-09-23 13:04:48","created_on":"2019-09-23 13:04:48","category_id":"3, 20","category_name":"Girls, Girls shirts","variant_id":"1, 6","variant_value":"small, white","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/WOND5K6sd7lTqnJh.jpg"},{"productId":"38","product_name":"Hoddie7","product_sku":"ss-22","regular_price":"4545","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"5445","collection_type_id":"1","in_stock":"1","status":"1","updated_on":"2019-09-23 13:04:09","created_on":"2019-09-23 13:04:09","category_id":"16, 8","category_name":"t-shirts, Kids ","variant_id":"5, 1","variant_value":"black, small","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/4FbhI17x96YuX3iW.jpg"},{"productId":"37","product_name":"Hoddie2","product_sku":"fgf","regular_price":"444","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"545","collection_type_id":"3","in_stock":"1","status":"1","updated_on":"2019-09-23 13:03:19","created_on":"2019-09-23 13:03:19","category_id":"2, 12","category_name":"Women, Jeans","variant_id":"6, 3","variant_value":"white, large","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/MRoiUJYPOGX0g2wQ.jpg"},{"productId":"36","product_name":"shirt3","product_sku":"ss-22","regular_price":"343","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"343","collection_type_id":"2","in_stock":"1","status":"1","updated_on":"2019-09-23 13:02:34","created_on":"2019-09-23 13:02:34","category_id":"5, 1","category_name":"T-shirt, Men","variant_id":"1, 5","variant_value":"small, black","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/n6KH0t1meiYhzw7F.jpg"},{"productId":"35","product_name":"shirt2","product_sku":"ss-22","regular_price":"322","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"232","collection_type_id":"3","in_stock":"1","status":"1","updated_on":"2019-09-23 13:01:50","created_on":"2019-09-23 13:01:50","category_id":"3, 17","category_name":"Girls, Shoes","variant_id":"6, 4","variant_value":"white, extra large","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/aYg6C3NFizPG4eKo.jpg"},{"productId":"34","product_name":"jeans","product_sku":"ss-22","regular_price":"343","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"434","collection_type_id":"2","in_stock":"1","status":"1","updated_on":"2019-09-23 13:00:58","created_on":"2019-09-23 13:00:58","category_id":"20, 3","category_name":"Girls shirts, Girls","variant_id":"5, 2","variant_value":"black, medium","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/xQFwkn2zf5mU9AHC.jpg"},{"productId":"33","product_name":"shirt","product_sku":"fg-25","regular_price":"232","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"232","collection_type_id":"3","in_stock":"1","status":"1","updated_on":"2019-09-23 12:59:49","created_on":"2019-09-23 12:59:49","category_id":"5, 1","category_name":"T-shirt, Men","variant_id":"6, 2","variant_value":"white, medium","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/u3sKXkdzoVNrHFjY.jpg"},{"productId":"32","product_name":"Hoddie","product_sku":"ss-22","regular_price":"2323","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"2323","collection_type_id":"3","in_stock":"1","status":"1","updated_on":"2019-09-23 12:59:02","created_on":"2019-09-23 12:59:02","category_id":"1, 4","category_name":"Men, Shirt","variant_id":"5, 1","variant_value":"black, small","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/Avew4qsxUH5GQ8b1.jpg"},{"productId":"31","product_name":"Hoddie","product_sku":"Hoo-60","regular_price":"800","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"0","collection_type_id":"1","in_stock":"1","status":"1","updated_on":"2019-09-23 12:14:06","created_on":"2019-09-20 14:32:34","category_id":"1, 4","category_name":"Men, Shirt","variant_id":"6, 2","variant_value":"white, medium","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/LyQBARreY1nTw35U.jpg"},{"productId":"30","product_name":"Denim Jacket","product_sku":"DJ-50","regular_price":"400","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"0","collection_type_id":"1","in_stock":"1","status":"1","updated_on":"2019-09-20 14:31:32","created_on":"2019-09-20 14:31:32","category_id":"1, 5","category_name":"Men, T-shirt","variant_id":"2, 3, 5, 6, 1","variant_value":"medium, large, black, white, small","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/KgJN675EdYhUiDoR.jpg"},{"productId":"25","product_name":"red designer shirts","product_sku":"ghd22das562das","regular_price":"750","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"600","collection_type_id":"2","in_stock":"1","status":"1","updated_on":"2019-09-20 13:36:30","created_on":"2019-09-20 10:14:53","category_id":"21, 2, 13","category_name":"Women shirts, Women, trouser","variant_id":"2, 4, 5","variant_value":"medium, extra large, black","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/lObT1GCRZQBt3nku.jpg"},{"productId":"24","product_name":"Check shirts","product_sku":"vds112dsds22","regular_price":"550","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"450","collection_type_id":"2","in_stock":"1","status":"1","updated_on":"2019-09-20 10:13:19","created_on":"2019-09-20 10:13:19","category_id":"21, 2","category_name":"Women shirts, Women","variant_id":"1, 6, 2, 4, 3, 5","variant_value":"small, white, medium, extra large, large, black","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/ylaORdEogsfemS6T.jpg"},{"productId":"23","product_name":"plain shirt","product_sku":"desw20dsd10","regular_price":"560","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"456","collection_type_id":"2","in_stock":"1","status":"1","updated_on":"2019-09-20 10:12:08","created_on":"2019-09-20 10:12:08","category_id":"2, 21","category_name":"Women, Women shirts","variant_id":"5, 2, 3","variant_value":"black, medium, large","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/5OLlGyXrqBmtFb2U.jpg"},{"productId":"22","product_name":"Jeans shirt","product_sku":"ddsa2dasdas5","regular_price":"4000","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"2000","collection_type_id":"2","in_stock":"1","status":"1","updated_on":"2019-09-20 10:10:41","created_on":"2019-09-20 10:10:41","category_id":"2, 21","category_name":"Women, Women shirts","variant_id":"2, 4, 5, 6","variant_value":"medium, extra large, black, white","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/iMmxPfI5gJNoQTLc.jpg"},{"productId":"21","product_name":"Shirts","product_sku":"ghgg111fvf10","regular_price":"2000","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"2500","collection_type_id":"2","in_stock":"1","status":"1","updated_on":"2019-09-20 10:08:15","created_on":"2019-09-20 10:08:15","category_id":"2, 21","category_name":"Women, Women shirts","variant_id":"6, 4, 5, 2","variant_value":"white, extra large, black, medium","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/KWZ6VePbaC8SA3jw.jpg"},{"productId":"20","product_name":"women shirts","product_sku":"hgj25ff55","regular_price":"500","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"0","collection_type_id":"2","in_stock":"1","status":"1","updated_on":"2019-09-20 10:03:39","created_on":"2019-09-20 10:03:39","category_id":"1, 4","category_name":"Men, Shirt","variant_id":"2, 5","variant_value":"medium, black","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/S3jA5oVkHfcp290g.jpg"},{"productId":"19","product_name":"footwear ","product_sku":"fvds13xdygsad1324","regular_price":"400","currency_code":"Euro","currency_symbol":"\u20ac","sale_price":"150","collection_type_id":"2","in_stock":"1","status":"1","updated_on":"2019-09-20 09:56:03","created_on":"2019-09-20 09:56:03","category_id":"2, 12","category_name":"Women, Jeans","variant_id":"3, 6","variant_value":"large, white","is_wishlist":"0","product_image":"https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/IQMNGF6ftDpdvrV2.jpg"}]}
     */

    private String status;
    private String message;
    private String maxPrice;
    private String minPrice;
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

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private String total_records;
        private List<ProductListBean> product_list;

        public String getTotal_records() {
            return total_records;
        }

        public void setTotal_records(String total_records) {
            this.total_records = total_records;
        }

        public List<ProductListBean> getProduct_list() {
            return product_list;
        }

        public void setProduct_list(List<ProductListBean> product_list) {
            this.product_list = product_list;
        }

        public static class ProductListBean {

            private String productId;
            private String product_name;
            private String discount;
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
            private String variant_id;
            private String variant_value;
            private String is_wishlist;
            private String product_image;
            private int hashCode;


            ///////////////BANNER IMAGE API////////////////////////
            private String banner_image;

            public String getBanner_image() {
                return banner_image;
            }

            public void setBanner_image(String banner_image) {
                this.banner_image = banner_image;
            }
            ///////////////////////////////////////////////////////


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

            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
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

            public String getVariant_id() {
                return variant_id;
            }

            public void setVariant_id(String variant_id) {
                this.variant_id = variant_id;
            }

            public String getVariant_value() {
                return variant_value;
            }

            public void setVariant_value(String variant_value) {
                this.variant_value = variant_value;
            }

            public String getIs_wishlist() {
                return is_wishlist;
            }

            public void setIs_wishlist(String is_wishlist) {
                this.is_wishlist = is_wishlist;
            }

            public String getProduct_image() {
                return product_image;
            }

            public void setProduct_image(String product_image) {
                this.product_image = product_image;
            }


            @Override
            public boolean equals(Object obj) {

                ProductListBean blog = (ProductListBean) obj;

                if (product_name.equals(blog.product_name) &&
                        product_image.equals(blog.product_image) &&
                        regular_price.equals(blog.regular_price) &&
                        sale_price.equals(blog.sale_price)) {
                    hashCode = blog.hashCode;
                    return true;
                } else {
                    hashCode = super.hashCode();
                    return false;
                }
            }

        }
    }
}
