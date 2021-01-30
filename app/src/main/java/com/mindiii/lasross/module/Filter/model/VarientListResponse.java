package com.mindiii.lasross.module.Filter.model;

import java.util.List;

public class VarientListResponse {

    /**
     * status : success
     * message : Found
     * data : {"variant":[{"variantId":"1","variant_name":"size","status":"1","updated_on":"2019-09-16 00:00:00","created_on":"2019-09-16 00:00:00","variant_value":[{"variantValueId":"1","variant_id":"1","variant_value":"small","status":"1","updated_on":"2019-09-16 00:00:00","created_on":"2019-09-16 00:00:00"},{"variantValueId":"2","variant_id":"1","variant_value":"medium","status":"1","updated_on":"2019-09-16 00:00:00","created_on":"2019-09-16 00:00:00"},{"variantValueId":"3","variant_id":"1","variant_value":"large","status":"1","updated_on":"2019-09-16 00:00:00","created_on":"2019-09-16 00:00:00"},{"variantValueId":"4","variant_id":"1","variant_value":"extra large","status":"1","updated_on":"2019-09-16 00:00:00","created_on":"2019-09-16 00:00:00"}]},{"variantId":"2","variant_name":"color","status":"1","updated_on":"2019-09-16 00:00:00","created_on":"2019-09-16 00:00:00","variant_value":[{"variantValueId":"5","variant_id":"2","variant_value":"black","status":"1","updated_on":"2019-09-16 00:00:00","created_on":"2019-09-16 00:00:00"},{"variantValueId":"6","variant_id":"2","variant_value":"white","status":"1","updated_on":"2019-09-16 00:00:00","created_on":"2019-09-16 00:00:00"}]}]}
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
        private List<VariantBean> variant;

        public List<VariantBean> getVariant() {
            return variant;
        }

        public void setVariant(List<VariantBean> variant) {
            this.variant = variant;
        }

        public static class VariantBean {
            /**
             * variantId : 1
             * variant_name : size
             * status : 1
             * updated_on : 2019-09-16 00:00:00
             * created_on : 2019-09-16 00:00:00
             * variant_value : [{"variantValueId":"1","variant_id":"1","variant_value":"small","status":"1","updated_on":"2019-09-16 00:00:00","created_on":"2019-09-16 00:00:00"},{"variantValueId":"2","variant_id":"1","variant_value":"medium","status":"1","updated_on":"2019-09-16 00:00:00","created_on":"2019-09-16 00:00:00"},{"variantValueId":"3","variant_id":"1","variant_value":"large","status":"1","updated_on":"2019-09-16 00:00:00","created_on":"2019-09-16 00:00:00"},{"variantValueId":"4","variant_id":"1","variant_value":"extra large","status":"1","updated_on":"2019-09-16 00:00:00","created_on":"2019-09-16 00:00:00"}]
             */

            private String variantId;
            private String variant_name;
            private String status;
            private String updated_on;
            private String created_on;
            private List<VariantValueBean> variant_value;

            public String getVariantId() {
                return variantId;
            }

            public void setVariantId(String variantId) {
                this.variantId = variantId;
            }

            public String getVariant_name() {
                return variant_name;
            }

            public void setVariant_name(String variant_name) {
                this.variant_name = variant_name;
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

            public List<VariantValueBean> getVariant_value() {
                return variant_value;
            }

            public void setVariant_value(List<VariantValueBean> variant_value) {
                this.variant_value = variant_value;
            }

            public static class VariantValueBean {
                /**
                 * variantValueId : 1
                 * variant_id : 1
                 * variant_value : small
                 * status : 1
                 * updated_on : 2019-09-16 00:00:00
                 * created_on : 2019-09-16 00:00:00
                 */

                private String variantValueId;
                private String variant_id;
                private String variant_value;
                private String status;
                private String updated_on;
                private String created_on;
                private boolean isTicked;

                public String getVariantValueId() {
                    return variantValueId;
                }

                public void setVariantValueId(String variantValueId) {
                    this.variantValueId = variantValueId;
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

                public boolean getIsTicked() {
                    return isTicked;
                }

                public void setIsTicked(boolean isTicked) {
                    this.isTicked = isTicked;
                }

            }
        }
    }
}
