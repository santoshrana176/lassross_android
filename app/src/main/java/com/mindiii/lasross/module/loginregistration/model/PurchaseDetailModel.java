package com.mindiii.lasross.module.loginregistration.model;

import java.util.List;

public class PurchaseDetailModel {

    private boolean status;
    private String message;
    private DataBeanX data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        private String id;
        private String object;
        private Object application_fee_percent;
        private String billing;
        private int billing_cycle_anchor;
        private Object billing_thresholds;
        private Object cancel_at;
        private boolean cancel_at_period_end;
        private Object canceled_at;
        private String collection_method;
        private int created;
        private int current_period_end;
        private int current_period_start;
        private String customer;
        private Object days_until_due;
        private Object default_payment_method;
        private Object default_source;
        private Object discount;
        private Object ended_at;
        private InvoiceCustomerBalanceSettingsBean invoice_customer_balance_settings;
        private ItemsBean items;
        private String latest_invoice;
        private boolean livemode;
        private Object next_pending_invoice_item_invoice;
        private Object pending_invoice_item_interval;
        private Object pending_setup_intent;
        private PlanBeanX plan;
        private int quantity;
        private Object schedule;
        private int start;
        private int start_date;
        private String status;
        private Object tax_percent;
        private Object trial_end;
        private Object trial_start;
        private List<?> default_tax_rates;
        private List<?> metadata;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public Object getApplication_fee_percent() {
            return application_fee_percent;
        }

        public void setApplication_fee_percent(Object application_fee_percent) {
            this.application_fee_percent = application_fee_percent;
        }

        public String getBilling() {
            return billing;
        }

        public void setBilling(String billing) {
            this.billing = billing;
        }

        public int getBilling_cycle_anchor() {
            return billing_cycle_anchor;
        }

        public void setBilling_cycle_anchor(int billing_cycle_anchor) {
            this.billing_cycle_anchor = billing_cycle_anchor;
        }

        public Object getBilling_thresholds() {
            return billing_thresholds;
        }

        public void setBilling_thresholds(Object billing_thresholds) {
            this.billing_thresholds = billing_thresholds;
        }

        public Object getCancel_at() {
            return cancel_at;
        }

        public void setCancel_at(Object cancel_at) {
            this.cancel_at = cancel_at;
        }

        public boolean isCancel_at_period_end() {
            return cancel_at_period_end;
        }

        public void setCancel_at_period_end(boolean cancel_at_period_end) {
            this.cancel_at_period_end = cancel_at_period_end;
        }

        public Object getCanceled_at() {
            return canceled_at;
        }

        public void setCanceled_at(Object canceled_at) {
            this.canceled_at = canceled_at;
        }

        public String getCollection_method() {
            return collection_method;
        }

        public void setCollection_method(String collection_method) {
            this.collection_method = collection_method;
        }

        public int getCreated() {
            return created;
        }

        public void setCreated(int created) {
            this.created = created;
        }

        public int getCurrent_period_end() {
            return current_period_end;
        }

        public void setCurrent_period_end(int current_period_end) {
            this.current_period_end = current_period_end;
        }

        public int getCurrent_period_start() {
            return current_period_start;
        }

        public void setCurrent_period_start(int current_period_start) {
            this.current_period_start = current_period_start;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public Object getDays_until_due() {
            return days_until_due;
        }

        public void setDays_until_due(Object days_until_due) {
            this.days_until_due = days_until_due;
        }

        public Object getDefault_payment_method() {
            return default_payment_method;
        }

        public void setDefault_payment_method(Object default_payment_method) {
            this.default_payment_method = default_payment_method;
        }

        public Object getDefault_source() {
            return default_source;
        }

        public void setDefault_source(Object default_source) {
            this.default_source = default_source;
        }

        public Object getDiscount() {
            return discount;
        }

        public void setDiscount(Object discount) {
            this.discount = discount;
        }

        public Object getEnded_at() {
            return ended_at;
        }

        public void setEnded_at(Object ended_at) {
            this.ended_at = ended_at;
        }

        public InvoiceCustomerBalanceSettingsBean getInvoice_customer_balance_settings() {
            return invoice_customer_balance_settings;
        }

        public void setInvoice_customer_balance_settings(InvoiceCustomerBalanceSettingsBean invoice_customer_balance_settings) {
            this.invoice_customer_balance_settings = invoice_customer_balance_settings;
        }

        public ItemsBean getItems() {
            return items;
        }

        public void setItems(ItemsBean items) {
            this.items = items;
        }

        public String getLatest_invoice() {
            return latest_invoice;
        }

        public void setLatest_invoice(String latest_invoice) {
            this.latest_invoice = latest_invoice;
        }

        public boolean isLivemode() {
            return livemode;
        }

        public void setLivemode(boolean livemode) {
            this.livemode = livemode;
        }

        public Object getNext_pending_invoice_item_invoice() {
            return next_pending_invoice_item_invoice;
        }

        public void setNext_pending_invoice_item_invoice(Object next_pending_invoice_item_invoice) {
            this.next_pending_invoice_item_invoice = next_pending_invoice_item_invoice;
        }

        public Object getPending_invoice_item_interval() {
            return pending_invoice_item_interval;
        }

        public void setPending_invoice_item_interval(Object pending_invoice_item_interval) {
            this.pending_invoice_item_interval = pending_invoice_item_interval;
        }

        public Object getPending_setup_intent() {
            return pending_setup_intent;
        }

        public void setPending_setup_intent(Object pending_setup_intent) {
            this.pending_setup_intent = pending_setup_intent;
        }

        public PlanBeanX getPlan() {
            return plan;
        }

        public void setPlan(PlanBeanX plan) {
            this.plan = plan;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public Object getSchedule() {
            return schedule;
        }

        public void setSchedule(Object schedule) {
            this.schedule = schedule;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getStart_date() {
            return start_date;
        }

        public void setStart_date(int start_date) {
            this.start_date = start_date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getTax_percent() {
            return tax_percent;
        }

        public void setTax_percent(Object tax_percent) {
            this.tax_percent = tax_percent;
        }

        public Object getTrial_end() {
            return trial_end;
        }

        public void setTrial_end(Object trial_end) {
            this.trial_end = trial_end;
        }

        public Object getTrial_start() {
            return trial_start;
        }

        public void setTrial_start(Object trial_start) {
            this.trial_start = trial_start;
        }

        public List<?> getDefault_tax_rates() {
            return default_tax_rates;
        }

        public void setDefault_tax_rates(List<?> default_tax_rates) {
            this.default_tax_rates = default_tax_rates;
        }

        public List<?> getMetadata() {
            return metadata;
        }

        public void setMetadata(List<?> metadata) {
            this.metadata = metadata;
        }

        public static class InvoiceCustomerBalanceSettingsBean {
            /**
             * consume_applied_balance_on_void : true
             */

            private boolean consume_applied_balance_on_void;

            public boolean isConsume_applied_balance_on_void() {
                return consume_applied_balance_on_void;
            }

            public void setConsume_applied_balance_on_void(boolean consume_applied_balance_on_void) {
                this.consume_applied_balance_on_void = consume_applied_balance_on_void;
            }
        }

        public static class ItemsBean {
            /**
             * object : list
             * data : [{"id":"si_G9qsxn0CaWy7SC","object":"subscription_item","billing_thresholds":null,"created":1573456719,"metadata":[],"plan":{"id":"plan_G8mLGcIvtv85YF","object":"plan","active":true,"aggregate_usage":null,"amount":200,"amount_decimal":"200","billing_scheme":"per_unit","created":1573209237,"currency":"eur","interval":"week","interval_count":1,"livemode":false,"metadata":[],"nickname":"lasross","product":"prod_G3SU73IRWrcV8P","tiers":null,"tiers_mode":null,"transform_usage":null,"trial_period_days":null,"usage_type":"licensed"},"quantity":1,"subscription":"sub_G9qso3f0kRmDeF","tax_rates":[]}]
             * has_more : false
             * total_count : 1
             * url : /v1/subscription_items?subscription=sub_G9qso3f0kRmDeF
             */

            private String object;
            private boolean has_more;
            private int total_count;
            private String url;
            private List<DataBean> data;

            public String getObject() {
                return object;
            }

            public void setObject(String object) {
                this.object = object;
            }

            public boolean isHas_more() {
                return has_more;
            }

            public void setHas_more(boolean has_more) {
                this.has_more = has_more;
            }

            public int getTotal_count() {
                return total_count;
            }

            public void setTotal_count(int total_count) {
                this.total_count = total_count;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * id : si_G9qsxn0CaWy7SC
                 * object : subscription_item
                 * billing_thresholds : null
                 * created : 1573456719
                 * metadata : []
                 * plan : {"id":"plan_G8mLGcIvtv85YF","object":"plan","active":true,"aggregate_usage":null,"amount":200,"amount_decimal":"200","billing_scheme":"per_unit","created":1573209237,"currency":"eur","interval":"week","interval_count":1,"livemode":false,"metadata":[],"nickname":"lasross","product":"prod_G3SU73IRWrcV8P","tiers":null,"tiers_mode":null,"transform_usage":null,"trial_period_days":null,"usage_type":"licensed"}
                 * quantity : 1
                 * subscription : sub_G9qso3f0kRmDeF
                 * tax_rates : []
                 */

                private String id;
                private String object;
                private Object billing_thresholds;
                private int created;
                private PlanBean plan;
                private int quantity;
                private String subscription;
                private List<?> metadata;
                private List<?> tax_rates;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getObject() {
                    return object;
                }

                public void setObject(String object) {
                    this.object = object;
                }

                public Object getBilling_thresholds() {
                    return billing_thresholds;
                }

                public void setBilling_thresholds(Object billing_thresholds) {
                    this.billing_thresholds = billing_thresholds;
                }

                public int getCreated() {
                    return created;
                }

                public void setCreated(int created) {
                    this.created = created;
                }

                public PlanBean getPlan() {
                    return plan;
                }

                public void setPlan(PlanBean plan) {
                    this.plan = plan;
                }

                public int getQuantity() {
                    return quantity;
                }

                public void setQuantity(int quantity) {
                    this.quantity = quantity;
                }

                public String getSubscription() {
                    return subscription;
                }

                public void setSubscription(String subscription) {
                    this.subscription = subscription;
                }

                public List<?> getMetadata() {
                    return metadata;
                }

                public void setMetadata(List<?> metadata) {
                    this.metadata = metadata;
                }

                public List<?> getTax_rates() {
                    return tax_rates;
                }

                public void setTax_rates(List<?> tax_rates) {
                    this.tax_rates = tax_rates;
                }

                public static class PlanBean {
                    /**
                     * id : plan_G8mLGcIvtv85YF
                     * object : plan
                     * active : true
                     * aggregate_usage : null
                     * amount : 200
                     * amount_decimal : 200
                     * billing_scheme : per_unit
                     * created : 1573209237
                     * currency : eur
                     * interval : week
                     * interval_count : 1
                     * livemode : false
                     * metadata : []
                     * nickname : lasross
                     * product : prod_G3SU73IRWrcV8P
                     * tiers : null
                     * tiers_mode : null
                     * transform_usage : null
                     * trial_period_days : null
                     * usage_type : licensed
                     */

                    private String id;
                    private String object;
                    private boolean active;
                    private Object aggregate_usage;
                    private int amount;
                    private String amount_decimal;
                    private String billing_scheme;
                    private int created;
                    private String currency;
                    private String interval;
                    private int interval_count;
                    private boolean livemode;
                    private String nickname;
                    private String product;
                    private Object tiers;
                    private Object tiers_mode;
                    private Object transform_usage;
                    private Object trial_period_days;
                    private String usage_type;
                    private List<?> metadata;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getObject() {
                        return object;
                    }

                    public void setObject(String object) {
                        this.object = object;
                    }

                    public boolean isActive() {
                        return active;
                    }

                    public void setActive(boolean active) {
                        this.active = active;
                    }

                    public Object getAggregate_usage() {
                        return aggregate_usage;
                    }

                    public void setAggregate_usage(Object aggregate_usage) {
                        this.aggregate_usage = aggregate_usage;
                    }

                    public int getAmount() {
                        return amount;
                    }

                    public void setAmount(int amount) {
                        this.amount = amount;
                    }

                    public String getAmount_decimal() {
                        return amount_decimal;
                    }

                    public void setAmount_decimal(String amount_decimal) {
                        this.amount_decimal = amount_decimal;
                    }

                    public String getBilling_scheme() {
                        return billing_scheme;
                    }

                    public void setBilling_scheme(String billing_scheme) {
                        this.billing_scheme = billing_scheme;
                    }

                    public int getCreated() {
                        return created;
                    }

                    public void setCreated(int created) {
                        this.created = created;
                    }

                    public String getCurrency() {
                        return currency;
                    }

                    public void setCurrency(String currency) {
                        this.currency = currency;
                    }

                    public String getInterval() {
                        return interval;
                    }

                    public void setInterval(String interval) {
                        this.interval = interval;
                    }

                    public int getInterval_count() {
                        return interval_count;
                    }

                    public void setInterval_count(int interval_count) {
                        this.interval_count = interval_count;
                    }

                    public boolean isLivemode() {
                        return livemode;
                    }

                    public void setLivemode(boolean livemode) {
                        this.livemode = livemode;
                    }

                    public String getNickname() {
                        return nickname;
                    }

                    public void setNickname(String nickname) {
                        this.nickname = nickname;
                    }

                    public String getProduct() {
                        return product;
                    }

                    public void setProduct(String product) {
                        this.product = product;
                    }

                    public Object getTiers() {
                        return tiers;
                    }

                    public void setTiers(Object tiers) {
                        this.tiers = tiers;
                    }

                    public Object getTiers_mode() {
                        return tiers_mode;
                    }

                    public void setTiers_mode(Object tiers_mode) {
                        this.tiers_mode = tiers_mode;
                    }

                    public Object getTransform_usage() {
                        return transform_usage;
                    }

                    public void setTransform_usage(Object transform_usage) {
                        this.transform_usage = transform_usage;
                    }

                    public Object getTrial_period_days() {
                        return trial_period_days;
                    }

                    public void setTrial_period_days(Object trial_period_days) {
                        this.trial_period_days = trial_period_days;
                    }

                    public String getUsage_type() {
                        return usage_type;
                    }

                    public void setUsage_type(String usage_type) {
                        this.usage_type = usage_type;
                    }

                    public List<?> getMetadata() {
                        return metadata;
                    }

                    public void setMetadata(List<?> metadata) {
                        this.metadata = metadata;
                    }
                }
            }
        }

        public static class PlanBeanX {
            /**
             * id : plan_G8mLGcIvtv85YF
             * object : plan
             * active : true
             * aggregate_usage : null
             * amount : 200
             * amount_decimal : 200
             * billing_scheme : per_unit
             * created : 1573209237
             * currency : eur
             * interval : week
             * interval_count : 1
             * livemode : false
             * metadata : []
             * nickname : lasross
             * product : prod_G3SU73IRWrcV8P
             * tiers : null
             * tiers_mode : null
             * transform_usage : null
             * trial_period_days : null
             * usage_type : licensed
             */

            private String id;
            private String object;
            private boolean active;
            private Object aggregate_usage;
            private int amount;
            private String amount_decimal;
            private String billing_scheme;
            private int created;
            private String currency;
            private String interval;
            private int interval_count;
            private boolean livemode;
            private String nickname;
            private String product;
            private Object tiers;
            private Object tiers_mode;
            private Object transform_usage;
            private Object trial_period_days;
            private String usage_type;
            private List<?> metadata;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getObject() {
                return object;
            }

            public void setObject(String object) {
                this.object = object;
            }

            public boolean isActive() {
                return active;
            }

            public void setActive(boolean active) {
                this.active = active;
            }

            public Object getAggregate_usage() {
                return aggregate_usage;
            }

            public void setAggregate_usage(Object aggregate_usage) {
                this.aggregate_usage = aggregate_usage;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getAmount_decimal() {
                return amount_decimal;
            }

            public void setAmount_decimal(String amount_decimal) {
                this.amount_decimal = amount_decimal;
            }

            public String getBilling_scheme() {
                return billing_scheme;
            }

            public void setBilling_scheme(String billing_scheme) {
                this.billing_scheme = billing_scheme;
            }

            public int getCreated() {
                return created;
            }

            public void setCreated(int created) {
                this.created = created;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getInterval() {
                return interval;
            }

            public void setInterval(String interval) {
                this.interval = interval;
            }

            public int getInterval_count() {
                return interval_count;
            }

            public void setInterval_count(int interval_count) {
                this.interval_count = interval_count;
            }

            public boolean isLivemode() {
                return livemode;
            }

            public void setLivemode(boolean livemode) {
                this.livemode = livemode;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getProduct() {
                return product;
            }

            public void setProduct(String product) {
                this.product = product;
            }

            public Object getTiers() {
                return tiers;
            }

            public void setTiers(Object tiers) {
                this.tiers = tiers;
            }

            public Object getTiers_mode() {
                return tiers_mode;
            }

            public void setTiers_mode(Object tiers_mode) {
                this.tiers_mode = tiers_mode;
            }

            public Object getTransform_usage() {
                return transform_usage;
            }

            public void setTransform_usage(Object transform_usage) {
                this.transform_usage = transform_usage;
            }

            public Object getTrial_period_days() {
                return trial_period_days;
            }

            public void setTrial_period_days(Object trial_period_days) {
                this.trial_period_days = trial_period_days;
            }

            public String getUsage_type() {
                return usage_type;
            }

            public void setUsage_type(String usage_type) {
                this.usage_type = usage_type;
            }

            public List<?> getMetadata() {
                return metadata;
            }

            public void setMetadata(List<?> metadata) {
                this.metadata = metadata;
            }
        }
    }
}
