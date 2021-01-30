package com.mindiii.lasross.temp_pojo;

public class Response {
    private Product product;
    private String status;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return
                "Response{" +
                        "product = '" + product + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}
