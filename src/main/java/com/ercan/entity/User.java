package com.ercan.entity;

import io.objectbox.BoxStore;
import io.objectbox.annotation.*;
import io.objectbox.relation.ToMany;

@Entity
public class User {
    @Id
    public long id;
    @Unique
    public String name;
    public String tel;
    public String address;

    @Convert(converter = UserType.UserTypeConverter.class, dbType = String.class)
    public UserType userType;

    @Backlink(to = "user")
    public ToMany<Purchase> purchases = new ToMany<>(this, com.ercan.entity.User_.purchases);

    @Backlink(to = "user")
    public ToMany<PaymentTransaction> transactions = new ToMany<>(this, com.ercan.entity.User_.transactions);

    public transient BoxStore __boxStore;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public ToMany<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(ToMany<Purchase> purchases) {
        this.purchases = purchases;
    }

    public ToMany<PaymentTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ToMany<PaymentTransaction> transactions) {
        this.transactions = transactions;
    }

    public User(){}
    public User(long id, String name, String tel, String address, UserType userType, ToMany<Purchase> purchases, ToMany<PaymentTransaction> transactions) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.userType = userType;
        this.purchases = purchases;
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name +
                ", tel='" + tel +
                ", address='" + address +
                ", userType='" + userType.getType() +
                ", purchases=" + purchasesToString() +
                ", transactions=" + transactionsToString() +
                '}';
    }

    public String purchasesToString() {
        StringBuilder s = new StringBuilder("{");
        for(Purchase purchase: purchases){
            s.append(purchase.toString()).append(",");
        }
        s.append("}");
        return s.toString();
    }

    public String transactionsToString() {
        StringBuilder s = new StringBuilder("{");
        for(PaymentTransaction transaction: transactions){
            s.append(transaction.toString()).append(",");
        }
        s.append("}");
        return s.toString();
    }
}
