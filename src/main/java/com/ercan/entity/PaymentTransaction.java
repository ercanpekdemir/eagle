package com.ercan.entity;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;


@Entity
public class PaymentTransaction {
    @Id
    public long id;
    public String date;
    public String paymentExplanation;
    @Convert(converter = PaymentType.PaymentTypeConverter.class, dbType = String.class)
    public PaymentType paymentType;
    public double amount;

    ToOne<User> user = new ToOne<>(this, com.ercan.entity.PaymentTransaction_.user);

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ToOne<User> getUser() {
        return user;
    }

    public void setUser(ToOne<User> user) {
        this.user = user;
    }

    public PaymentTransaction(){}

    public PaymentTransaction(long id, String date, String paymentExplanation, PaymentType paymentType, double amount, ToOne<User> user) {
        this.id = id;
        this.date = date;
        this.paymentExplanation = paymentExplanation;
        this.paymentType = paymentType;
        this.amount = amount;
        this.user = user;
    }

    @Override
    public String toString() {
        return "PaymentTransaction{" +
                "id=" + id +
                ", date=" + date +
                ", paymentExplanation='" + paymentExplanation + '\'' +
                ", paymentType=" + paymentType.toString() +
                ", amount=" + amount +
                ", user=" + user.getTarget().name +
                '}';
    }
}
