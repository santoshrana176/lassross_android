package com.mindiii.lasross.module.loginregistration.model;

public class Capabilities {
    private boolean customer;

    public boolean isCustomer() {
        return customer;
    }

    public void setCustomer(boolean customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return
                "Capabilities{" +
                        "customer = '" + customer + '\'' +
                        "}";
    }
}
