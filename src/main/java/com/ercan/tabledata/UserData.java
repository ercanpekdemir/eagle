package com.ercan.tabledata;

import java.util.List;

public class UserData {

    public String name;
    public String tel;
    public String address;
    public String userType;
    public List<PurchaseData> purchases;
    public List<PaymentTransactionData> transactions;

    public UserData(String name, String tel, String address, String userType, List<PurchaseData> purchases, List<PaymentTransactionData> transactions) {
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.userType = userType;
        this.purchases = purchases;
        this.transactions = transactions;
    }

    public UserData() {}

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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<PurchaseData> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseData> purchases) {
        this.purchases = purchases;
    }

    public List<PaymentTransactionData> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<PaymentTransactionData> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", userType='" + userType + '\'' +
                ", purchases=" + purchases +
                ", transactions=" + transactions +
                '}';
    }
}
