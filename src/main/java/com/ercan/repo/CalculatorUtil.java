package com.ercan.repo;

import com.ercan.entity.*;

import java.text.DecimalFormat;

public class CalculatorUtil {
    public static Balance calculateBalance(PaymentTransaction transaction, Balance balance) {
        if(PaymentType.CASH.getType().equals(transaction.paymentType.getType())) {
            balance.totalCashPayment += transaction.amount;
        } else if(PaymentType.CC.getType().equals(transaction.paymentType.getType())) {
            balance.totalCCPayment += transaction.amount;
        } else if(PaymentType.CEK.getType().equals(transaction.paymentType.getType())) {
            balance.totalCEKPayment += transaction.amount;
        } else if(PaymentType.RETURN.getType().equals(transaction.paymentType.getType())) {
            balance.totalReturnPayment += transaction.amount;
        }

        balance.totalTahsilat = balance.totalCashPayment
                + balance.totalCCPayment
                + balance.totalCEKPayment
                - balance.totalReturnPayment;

        balance.balance = balance.totalPurchase - balance.totalTahsilat;

        return balance;
    }

    public static Balance widthDrawBalance(PaymentTransaction transaction, Balance balance) {
        if(PaymentType.CASH.getType().equals(transaction.paymentType.getType())) {
            balance.totalCashPayment -= transaction.amount;
        } else if(PaymentType.CC.getType().equals(transaction.paymentType.getType())) {
            balance.totalCCPayment -= transaction.amount;
        } else if(PaymentType.CEK.getType().equals(transaction.paymentType.getType())) {
            balance.totalCEKPayment -= transaction.amount;
        } else if(PaymentType.RETURN.getType().equals(transaction.paymentType.getType())) {
            balance.totalReturnPayment -= transaction.amount;
        }

        balance.totalTahsilat = balance.totalCashPayment
                + balance.totalCCPayment
                + balance.totalCEKPayment
                - balance.totalReturnPayment;

        balance.balance = balance.totalPurchase - balance.totalTahsilat;

        return balance;
    }

    public static Balance calculateBalance(Purchase purchase, Balance balance) {
        balance.totalPurchase += purchase.price;
        balance.balance = balance.totalPurchase - balance.totalTahsilat;
        return balance;
    }

    public static Balance widthDrawBalance(Purchase purchase, Balance balance) {
        balance.totalPurchase -= purchase.price;
        balance.balance = balance.totalPurchase - balance.totalTahsilat;
        return balance;
    }

    public static String amountFormatter(double amount) {
        String pattern = "###,###.##";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        String format = decimalFormat.format(amount);
        return format + " TL";
    }
}
