package com.ercan.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Balance {
    @Id
    public long id;
    public double totalPurchase;
    public double totalCashPayment;
    public double totalCCPayment;
    public double totalCEKPayment;
    public double totalReturnPayment;
    public double totalTahsilat;
    public double balance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(double totalPurchase) {
        this.totalPurchase = totalPurchase;
    }

    public double getTotalCashPayment() {
        return totalCashPayment;
    }

    public void setTotalCashPayment(double totalCashPayment) {
        this.totalCashPayment = totalCashPayment;
    }

    public double getTotalCCPayment() {
        return totalCCPayment;
    }

    public void setTotalCCPayment(double totalCCPayment) {
        this.totalCCPayment = totalCCPayment;
    }

    public double getTotalCEKPayment() {
        return totalCEKPayment;
    }

    public void setTotalCEKPayment(double totalCEKPayment) {
        this.totalCEKPayment = totalCEKPayment;
    }

    public double getTotalReturnPayment() {
        return totalReturnPayment;
    }

    public void setTotalReturnPayment(double totalReturnPayment) {
        this.totalReturnPayment = totalReturnPayment;
    }

    public double getTotalTahsilat() {
        return totalTahsilat;
    }

    public void setTotalTahsilat(double totalTahsilat) {
        this.totalTahsilat = totalTahsilat;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ToOne<User> getUser() {
        return user;
    }

    public void setUser(ToOne<User> user) {
        this.user = user;
    }

    public ToOne<User> user = new ToOne(this, Balance_.user);;

    public Balance(){}

    public Balance(long id, double totalPurchase, double totalCashPayment, double totalCCPayment, double totalCEKPayment, double totalReturnPayment, double totalTahsilat, double balance) {
        this.id = id;
        this.totalPurchase = totalPurchase;
        this.totalCashPayment = totalCashPayment;
        this.totalCCPayment = totalCCPayment;
        this.totalCEKPayment = totalCEKPayment;
        this.totalReturnPayment = totalReturnPayment;
        this.totalTahsilat = totalTahsilat;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", totalPurchase=" + totalPurchase +
                ", totalCashPayment=" + totalCashPayment +
                ", totalCCPayment=" + totalCCPayment +
                ", totalCEKPayment=" + totalCEKPayment +
                ", totalReturnPayment=" + totalReturnPayment +
                ", totalTahsilat=" + totalTahsilat +
                ", balance=" + balance +
//                ", user=" + user.getTarget().name +
                '}';
    }
}
