package com.ercan.tabledata;

import com.ercan.repo.CalculatorUtil;

public class PaymentTransactionData {
    public long id;
    public String date;
    public String paymentExplanation;
    public String paymentType;
    public String amount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaymentExplanation() {
        return paymentExplanation;
    }

    public void setPaymentExplanation(String paymentExplanation) {
        this.paymentExplanation = paymentExplanation;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PaymentTransactionData(long id, String date, String paymentExplanation, String paymentType, String amount) {
        this.id = id;
        this.date = date;
        this.paymentExplanation = paymentExplanation;
        this.paymentType = paymentType;
        this.amount = amount;
    }
    public PaymentTransactionData() {

    }

    @Override
    public String toString() {
        return "PaymentTransactionData{" +
                "date='" + date + '\'' +
                ", paymentExplanation='" + paymentExplanation + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", amount=" + amount +
                '}';
    }

    public PaymentTransactionData copy() {
        return new PaymentTransactionData(
                id,
                date,
                paymentExplanation,
                paymentType,
                CalculatorUtil.amountFormatter(Double.parseDouble(amount))
        );
    }
}
