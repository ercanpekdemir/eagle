package com.ercan.tabledata;

public class BalanceData {

    public double totalPurchase;
    public double totalCashPayment;
    public double totalCCPayment;
    public double totalCEKPayment;
    public double totalReturnPayment;
    public double totalTahsilat;
    public double balance;

    public BalanceData(double totalPurchase, double totalCashPayment, double totalCCPayment, double totalCEKPayment, double totalReturnPayment, double totalTahsilat, double balance) {
        this.totalPurchase = totalPurchase;
        this.totalCashPayment = totalCashPayment;
        this.totalCCPayment = totalCCPayment;
        this.totalCEKPayment = totalCEKPayment;
        this.totalReturnPayment = totalReturnPayment;
        this.totalTahsilat = totalTahsilat;
        this.balance = balance;
    }

    public BalanceData() {}

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

    @Override
    public String toString() {
        return "BalanceData{" +
                "totalPurchase=" + totalPurchase +
                ", totalCashPayment=" + totalCashPayment +
                ", totalCCPayment=" + totalCCPayment +
                ", totalCEKPayment=" + totalCEKPayment +
                ", totalReturnPayment=" + totalReturnPayment +
                ", totalTahsilat=" + totalTahsilat +
                ", balance=" + balance +
                '}';
    }
}
