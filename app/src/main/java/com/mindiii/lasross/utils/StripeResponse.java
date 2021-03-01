package com.mindiii.lasross.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StripeResponse {


    @SerializedName("account_balance")
    @Expose
    private Object accountBalance;
    @SerializedName("business_vat_id")
    @Expose
    private Object businessVatId;
    @SerializedName("cards")
    @Expose
    private Object cards;
    @SerializedName("created")
    @Expose
    private Integer created;
    @SerializedName("currency")
    @Expose
    private Object currency;
    @SerializedName("default_card")
    @Expose
    private Object defaultCard;
    @SerializedName("default_source")
    @Expose
    private String defaultSource;
    @SerializedName("deleted")
    @Expose
    private Object deleted;
    @SerializedName("delinquent")
    @Expose
    private Boolean delinquent;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("discount")
    @Expose
    private Object discount;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("invoice_prefix")
    @Expose
    private String invoicePrefix;
    @SerializedName("livemode")
    @Expose
    private Boolean livemode;
    @SerializedName("metadata")
    @Expose
    private Metadata metadata;
    @SerializedName("next_recurring_charge")
    @Expose
    private Object nextRecurringCharge;
    @SerializedName("object")
    @Expose
    private String object;
    @SerializedName("shipping")
    @Expose
    private Object shipping;
    @SerializedName("sources")
    @Expose
    private Sources sources;
    @SerializedName("subscription")
    @Expose
    private Object subscription;
    @SerializedName("subscriptions")
    @Expose
    private Object subscriptions;
    @SerializedName("tax_info")
    @Expose
    private Object taxInfo;
    @SerializedName("tax_info_verification")
    @Expose
    private Object taxInfoVerification;
    @SerializedName("trial_end")
    @Expose
    private Object trialEnd;

    public Object getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Object accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Object getBusinessVatId() {
        return businessVatId;
    }

    public void setBusinessVatId(Object businessVatId) {
        this.businessVatId = businessVatId;
    }

    public Object getCards() {
        return cards;
    }

    public void setCards(Object cards) {
        this.cards = cards;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Object getCurrency() {
        return currency;
    }

    public void setCurrency(Object currency) {
        this.currency = currency;
    }

    public Object getDefaultCard() {
        return defaultCard;
    }

    public void setDefaultCard(Object defaultCard) {
        this.defaultCard = defaultCard;
    }

    public String getDefaultSource() {
        return defaultSource;
    }

    public void setDefaultSource(String defaultSource) {
        this.defaultSource = defaultSource;
    }

    public Object getDeleted() {
        return deleted;
    }

    public void setDeleted(Object deleted) {
        this.deleted = deleted;
    }

    public Boolean getDelinquent() {
        return delinquent;
    }

    public void setDelinquent(Boolean delinquent) {
        this.delinquent = delinquent;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getDiscount() {
        return discount;
    }

    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvoicePrefix() {
        return invoicePrefix;
    }

    public void setInvoicePrefix(String invoicePrefix) {
        this.invoicePrefix = invoicePrefix;
    }

    public Boolean getLivemode() {
        return livemode;
    }

    public void setLivemode(Boolean livemode) {
        this.livemode = livemode;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Object getNextRecurringCharge() {
        return nextRecurringCharge;
    }

    public void setNextRecurringCharge(Object nextRecurringCharge) {
        this.nextRecurringCharge = nextRecurringCharge;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Object getShipping() {
        return shipping;
    }

    public void setShipping(Object shipping) {
        this.shipping = shipping;
    }

    public Sources getSources() {
        return sources;
    }

    public void setSources(Sources sources) {
        this.sources = sources;
    }

    public Object getSubscription() {
        return subscription;
    }

    public void setSubscription(Object subscription) {
        this.subscription = subscription;
    }

    public Object getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Object subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Object getTaxInfo() {
        return taxInfo;
    }

    public void setTaxInfo(Object taxInfo) {
        this.taxInfo = taxInfo;
    }

    public Object getTaxInfoVerification() {
        return taxInfoVerification;
    }

    public void setTaxInfoVerification(Object taxInfoVerification) {
        this.taxInfoVerification = taxInfoVerification;
    }

    public Object getTrialEnd() {
        return trialEnd;
    }

    public void setTrialEnd(Object trialEnd) {
        this.trialEnd = trialEnd;
    }


    public class Metadata_ {


    }

    public class Metadata {


    }

    public class Datum {
        private String isSelected = "No";

        public String getIsSelected() {
            return isSelected;
        }

        public void setIsSelected(String isSelected) {
            this.isSelected = isSelected;
        }

        public boolean isMoreDetail() {
            return isMoreDetail;
        }

        public void setMoreDetail(boolean moreDetail) {
            isMoreDetail = moreDetail;
        }

        private boolean isMoreDetail;

        @SerializedName("address_city")
        @Expose
        private Object addressCity;
        @SerializedName("address_country")
        @Expose
        private Object addressCountry;
        @SerializedName("address_line1")
        @Expose
        private Object addressLine1;
        @SerializedName("address_line1_check")
        @Expose
        private Object addressLine1Check;
        @SerializedName("address_line2")
        @Expose
        private Object addressLine2;
        @SerializedName("address_state")
        @Expose
        private Object addressState;
        @SerializedName("address_zip")
        @Expose
        private Object addressZip;
        @SerializedName("address_zip_check")
        @Expose
        private Object addressZipCheck;
        @SerializedName("available_payout_methods")
        @Expose
        private Object availablePayoutMethods;
        @SerializedName("brand")
        @Expose
        private String brand;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("currency")
        @Expose
        private Object currency;
        @SerializedName("cvc_check")
        @Expose
        private String cvcCheck;
        @SerializedName("default_for_currency")
        @Expose
        private Object defaultForCurrency;
        @SerializedName("deleted")
        @Expose
        private Object deleted;
        @SerializedName("description")
        @Expose
        private Object description;
        @SerializedName("dynamic_last4")
        @Expose
        private Object dynamicLast4;
        @SerializedName("exp_month")
        @Expose
        private Integer expMonth;
        @SerializedName("exp_year")
        @Expose
        private Integer expYear;
        @SerializedName("fingerprint")
        @Expose
        private String fingerprint;
        @SerializedName("funding")
        @Expose
        private String funding;
        @SerializedName("iin")
        @Expose
        private Object iin;
        @SerializedName("issuer")
        @Expose
        private Object issuer;
        @SerializedName("last4")
        @Expose
        private String last4;
        @SerializedName("name")
        @Expose
        private Object name;
        @SerializedName("recipient")
        @Expose
        private Object recipient;
        @SerializedName("status")
        @Expose
        private Object status;
        @SerializedName("three_d_secure")
        @Expose
        private Object threeDSecure;
        @SerializedName("tokenization_method")
        @Expose
        private Object tokenizationMethod;
        @SerializedName("type")
        @Expose
        private Object type;
        @SerializedName("account")
        @Expose
        private Object account;
        @SerializedName("customer")
        @Expose
        private String customer;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("metadata")
        @Expose
        private Metadata_ metadata;
        @SerializedName("object")
        @Expose
        private String object;

        public Object getAddressCity() {
            return addressCity;
        }

        public void setAddressCity(Object addressCity) {
            this.addressCity = addressCity;
        }

        public Object getAddressCountry() {
            return addressCountry;
        }

        public void setAddressCountry(Object addressCountry) {
            this.addressCountry = addressCountry;
        }

        public Object getAddressLine1() {
            return addressLine1;
        }

        public void setAddressLine1(Object addressLine1) {
            this.addressLine1 = addressLine1;
        }

        public Object getAddressLine1Check() {
            return addressLine1Check;
        }

        public void setAddressLine1Check(Object addressLine1Check) {
            this.addressLine1Check = addressLine1Check;
        }

        public Object getAddressLine2() {
            return addressLine2;
        }

        public void setAddressLine2(Object addressLine2) {
            this.addressLine2 = addressLine2;
        }

        public Object getAddressState() {
            return addressState;
        }

        public void setAddressState(Object addressState) {
            this.addressState = addressState;
        }

        public Object getAddressZip() {
            return addressZip;
        }

        public void setAddressZip(Object addressZip) {
            this.addressZip = addressZip;
        }

        public Object getAddressZipCheck() {
            return addressZipCheck;
        }

        public void setAddressZipCheck(Object addressZipCheck) {
            this.addressZipCheck = addressZipCheck;
        }

        public Object getAvailablePayoutMethods() {
            return availablePayoutMethods;
        }

        public void setAvailablePayoutMethods(Object availablePayoutMethods) {
            this.availablePayoutMethods = availablePayoutMethods;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Object getCurrency() {
            return currency;
        }

        public void setCurrency(Object currency) {
            this.currency = currency;
        }

        public String getCvcCheck() {
            return cvcCheck;
        }

        public void setCvcCheck(String cvcCheck) {
            this.cvcCheck = cvcCheck;
        }

        public Object getDefaultForCurrency() {
            return defaultForCurrency;
        }

        public void setDefaultForCurrency(Object defaultForCurrency) {
            this.defaultForCurrency = defaultForCurrency;
        }

        public Object getDeleted() {
            return deleted;
        }

        public void setDeleted(Object deleted) {
            this.deleted = deleted;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public Object getDynamicLast4() {
            return dynamicLast4;
        }

        public void setDynamicLast4(Object dynamicLast4) {
            this.dynamicLast4 = dynamicLast4;
        }

        public Integer getExpMonth() {
            return expMonth;
        }

        public void setExpMonth(Integer expMonth) {
            this.expMonth = expMonth;
        }

        public Integer getExpYear() {
            return expYear;
        }

        public void setExpYear(Integer expYear) {
            this.expYear = expYear;
        }

        public String getFingerprint() {
            return fingerprint;
        }

        public void setFingerprint(String fingerprint) {
            this.fingerprint = fingerprint;
        }

        public String getFunding() {
            return funding;
        }

        public void setFunding(String funding) {
            this.funding = funding;
        }

        public Object getIin() {
            return iin;
        }

        public void setIin(Object iin) {
            this.iin = iin;
        }

        public Object getIssuer() {
            return issuer;
        }

        public void setIssuer(Object issuer) {
            this.issuer = issuer;
        }

        public String getLast4() {
            return last4;
        }

        public void setLast4(String last4) {
            this.last4 = last4;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getRecipient() {
            return recipient;
        }

        public void setRecipient(Object recipient) {
            this.recipient = recipient;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getThreeDSecure() {
            return threeDSecure;
        }

        public void setThreeDSecure(Object threeDSecure) {
            this.threeDSecure = threeDSecure;
        }

        public Object getTokenizationMethod() {
            return tokenizationMethod;
        }

        public void setTokenizationMethod(Object tokenizationMethod) {
            this.tokenizationMethod = tokenizationMethod;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getAccount() {
            return account;
        }

        public void setAccount(Object account) {
            this.account = account;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Metadata_ getMetadata() {
            return metadata;
        }

        public void setMetadata(Metadata_ metadata) {
            this.metadata = metadata;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

    }

    public class Sources {

        @SerializedName("count")
        @Expose
        private Object count;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;
        @SerializedName("has_more")
        @Expose
        private Boolean hasMore;
        @SerializedName("object")
        @Expose
        private String object;
        @SerializedName("request_options")
        @Expose
        private Object requestOptions;
        @SerializedName("request_params")
        @Expose
        private Object requestParams;
        @SerializedName("total_count")
        @Expose
        private Integer totalCount;
        @SerializedName("url")
        @Expose
        private String url;

        public Object getCount() {
            return count;
        }

        public void setCount(Object count) {
            this.count = count;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

        public Boolean getHasMore() {
            return hasMore;
        }

        public void setHasMore(Boolean hasMore) {
            this.hasMore = hasMore;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public Object getRequestOptions() {
            return requestOptions;
        }

        public void setRequestOptions(Object requestOptions) {
            this.requestOptions = requestOptions;
        }

        public Object getRequestParams() {
            return requestParams;
        }

        public void setRequestParams(Object requestParams) {
            this.requestParams = requestParams;
        }

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }

}
