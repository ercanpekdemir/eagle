package com.ercan.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;

@Entity
public class Product {
    @Id
    public long id;
    @Unique
    public String productExplanation;

    public Product() {}

    public Product(long id, String productExplanation) {
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
