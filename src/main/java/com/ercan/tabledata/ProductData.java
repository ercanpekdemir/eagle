package com.ercan.tabledata;

public class ProductData {
    public long id;
    public String productExplanation;

    public ProductData() {}

    public ProductData(long id, String productExplanation) {
        this.id = id;
        this.productExplanation = productExplanation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductExplanation() {
        return productExplanation;
    }

    public void setProductExplanation(String productExplanation) {
        this.productExplanation = productExplanation;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productExplanation='" + productExplanation + '\'' +
                '}';
    }
}
