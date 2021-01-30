package com.mindiii.lasross.module.home.model;

import java.util.List;

public class MenuSliderResponse {

    /**
     * status : success
     * message : Found
     * data : {"category":[{"categoryId":"1","category_name":"Men","category_description":"This is for Men","category_type":"product","parent_category_id":"0","created_on":"2019-09-09 06:06:39","sub_category":[{"categoryId":"4","category_name":"Shirt","category_description":"Lorem Ipsum is simply dummy text of the printing and typesetting industry.","category_type":"product","parent_category_id":"1","created_on":"2019-09-09 06:08:22"},{"categoryId":"5","category_name":"T-shirt","category_description":"Lorem Ipsum is simply dummy text of the printing and typesetting industry.","category_type":"product","parent_category_id":"1","created_on":"2019-09-09 06:08:54"}]},{"categoryId":"2","category_name":"Women","category_description":"This is for Women","category_type":"product","parent_category_id":"0","created_on":"2019-09-09 06:07:04","sub_category":[{"categoryId":"12","category_name":"Jeans","category_description":"girls wear","category_type":"product","parent_category_id":"2","created_on":"2019-09-18 14:05:23"},{"categoryId":"13","category_name":"trouser","category_description":"girls wear","category_type":"product","parent_category_id":"2","created_on":"2019-09-18 14:05:43"}]},{"categoryId":"3","category_name":"Girls","category_description":"This is for Kids below age 15 ","category_type":"product","parent_category_id":"0","created_on":"2019-09-09 06:07:37","sub_category":[{"categoryId":"7","category_name":"shirts","category_description":"designer shirts for girls","category_type":"product","parent_category_id":"3","created_on":"2019-09-09 10:54:29"}]},{"categoryId":"6","category_name":"Boy","category_description":"baby boy's wear","category_type":"product","parent_category_id":"0","created_on":"2019-09-09 10:31:33","sub_category":[{"categoryId":"15","category_name":"jeans","category_description":"baby boy","category_type":"product","parent_category_id":"6","created_on":"2019-09-18 14:06:51"}]},{"categoryId":"8","category_name":"Kids ","category_description":"girls wear","category_type":"product","parent_category_id":"0","created_on":"2019-09-12 10:55:34","sub_category":[{"categoryId":"14","category_name":"shirts","category_description":"children wears","category_type":"product","parent_category_id":"8","created_on":"2019-09-18 14:06:21"},{"categoryId":"16","category_name":"t-shirts","category_description":"baby boy","category_type":"product","parent_category_id":"8","created_on":"2019-09-18 14:07:20"}]}]}
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
        private List<CategoryBean> category;

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public static class CategoryBean {
            /**
             * categoryId : 1
             * category_name : Men
             * category_description : This is for Men
             * category_type : product
             * parent_category_id : 0
             * created_on : 2019-09-09 06:06:39
             * sub_category : [{"categoryId":"4","category_name":"Shirt","category_description":"Lorem Ipsum is simply dummy text of the printing and typesetting industry.","category_type":"product","parent_category_id":"1","created_on":"2019-09-09 06:08:22"},{"categoryId":"5","category_name":"T-shirt","category_description":"Lorem Ipsum is simply dummy text of the printing and typesetting industry.","category_type":"product","parent_category_id":"1","created_on":"2019-09-09 06:08:54"}]
             */

            private String categoryId;
            private String category_name;
            private String category_description;
            private String category_type;
            private String parent_category_id;
            private String created_on;
            private List<SubCategoryBean> sub_category;

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getCategory_name() {
                return category_name;
            }

            public void setCategory_name(String category_name) {
                this.category_name = category_name;
            }

            public String getCategory_description() {
                return category_description;
            }

            public void setCategory_description(String category_description) {
                this.category_description = category_description;
            }

            public String getCategory_type() {
                return category_type;
            }

            public void setCategory_type(String category_type) {
                this.category_type = category_type;
            }

            public String getParent_category_id() {
                return parent_category_id;
            }

            public void setParent_category_id(String parent_category_id) {
                this.parent_category_id = parent_category_id;
            }

            public String getCreated_on() {
                return created_on;
            }

            public void setCreated_on(String created_on) {
                this.created_on = created_on;
            }

            public List<SubCategoryBean> getSub_category() {
                return sub_category;
            }

            public void setSub_category(List<SubCategoryBean> sub_category) {
                this.sub_category = sub_category;
            }

            public static class SubCategoryBean {
                /**
                 * categoryId : 4
                 * category_name : Shirt
                 * category_description : Lorem Ipsum is simply dummy text of the printing and typesetting industry.
                 * category_type : product
                 * parent_category_id : 1
                 * created_on : 2019-09-09 06:08:22
                 */

                private String categoryId;
                private String category_name;
                private String category_description;
                private String category_type;
                private String parent_category_id;
                private String created_on;

                public String getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(String categoryId) {
                    this.categoryId = categoryId;
                }

                public String getCategory_name() {
                    return category_name;
                }

                public void setCategory_name(String category_name) {
                    this.category_name = category_name;
                }

                public String getCategory_description() {
                    return category_description;
                }

                public void setCategory_description(String category_description) {
                    this.category_description = category_description;
                }

                public String getCategory_type() {
                    return category_type;
                }

                public void setCategory_type(String category_type) {
                    this.category_type = category_type;
                }

                public String getParent_category_id() {
                    return parent_category_id;
                }

                public void setParent_category_id(String parent_category_id) {
                    this.parent_category_id = parent_category_id;
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
}
