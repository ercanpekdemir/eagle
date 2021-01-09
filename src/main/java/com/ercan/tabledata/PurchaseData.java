package com.ercan.tabledata;

import com.ercan.repo.CalculatorUtil;

public class PurchaseData {
    public long id;
    public String date;
    public String productExplanation;
    public String unit;
    public double quantity;
    public String unitPrice;
    public String price;

    public PurchaseData(long id, String date, String productExplanation, String unit, double quantity, String unitPrice, String price) {
        this.id = id;
        this.date = date;
        this.productExplanation = productExplanation;
        this.unit = unit;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.price = price;
    }

    public PurchaseData(){}

    @Override
    public String toString() {
        return "PurchaseData{" +
                "date=" + date +
                ", productExplanation='" + productExplanation + '\'' +
                ", unit='" + unit + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", price=" + price +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String  date) {
        this.date = date;
    }

    public String getProductExplanation() {
        return productExplanation;
    }

    public void setProductExplanation(String productExplanation) {
        this.productExplanation = productExplanation;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public PurchaseData copy() {
        return new PurchaseData(
            id,
            date,
            productExplanation,
            unit,
            quantity,
            CalculatorUtil.amountFormatter(Double.parseDouble(unitPrice)),
            CalculatorUtil.amountFormatter(Double.parseDouble(price))
        );
    }
}
