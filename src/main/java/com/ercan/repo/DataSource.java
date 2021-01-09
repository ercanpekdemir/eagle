package com.ercan.repo;

import com.ercan.entity.*;
import com.ercan.entity.Balance_;
import com.ercan.entity.PaymentTransaction_;
import com.ercan.entity.Purchase_;
import com.ercan.entity.User_;
import com.ercan.tabledata.PaymentTransactionData;
import com.ercan.tabledata.PurchaseData;
import com.ercan.tabledata.UserData;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.exception.UniqueViolationException;
import io.objectbox.query.QueryBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


public class DataSource {

    private Box<User> userBox;
    private Box<Purchase> purchaseBox;
    private Box<PaymentTransaction> paymentTransactionBox;
    private Box<Balance> balanceBox;

    public DataSource(BoxStore boxStore) {
        userBox = boxStore.boxFor(User.class);
        purchaseBox = boxStore.boxFor(Purchase.class);
        paymentTransactionBox = boxStore.boxFor(PaymentTransaction.class);
        balanceBox = boxStore.boxFor(Balance.class);
    }

    public boolean newUser(String name, String tel, String address, UserType userType) {
        User user = new User();
        user.name = name;
        user.tel = tel;
        user.address = address;
        user.userType = userType;

        try {
            userBox.put(user);
        }catch (UniqueViolationException e){
            return false;
        }

        Balance balance = new Balance();
        balance.user.setTarget(user);
        balanceBox.put(balance);

        return true;
    }

    public User findUser(String name, UserType userType) {
        return userBox.query()
                .equal(User_.name, name)
                .and()
                .equal(User_.userType, userType.getType())
                .build()
                .findFirst();
    }

    public List<User> searchUser(String key, UserType userType) {
        return userBox.query()
                .startsWith(User_.name, key)
                .or()
                .contains(User_.name, key)
                .and()
                .equal(User_.userType, userType.getType())
                .build()
                .find();
    }

    public void updateUser(User user, UserData userData) {
        user.name = userData.name;
        user.tel = userData.tel;
        user.address = userData.address;
        userBox.put(user);
    }

    public boolean removeUser(User user) {
        return userBox.remove(user);
    }

    public boolean isUserExist(User user) {
        return userBox.get(user.id) != null;
    }

    public Balance findBalanceByUser(User user) {
//        return user.balance.getTarget();
        return balanceBox.query()
                .equal(Balance_.userId, user.id)
                .build()
                .findFirst();
    }

    public List<Purchase> findPurchasesByUser(User user) {
        return purchaseBox.query()
                .equal(Purchase_.userId, user.id)
                .build()
                .find();
    }

    public List<PaymentTransaction> findPaymentsByUser(User user) {
        return paymentTransactionBox.query()
                .equal(PaymentTransaction_.userId, user.id)
                .build()
                .find();
    }

    public void newPurchase(User user,
                            String d,
                            String productExplanation,
                            Unit unit,
                            double quantity,
                            double unitPrice) {

        String date;
        if(d == null) {
            date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } else {
            date = d;
        }
        Purchase purchase = new Purchase();
        purchase.date = date;
        purchase.productExplanation = productExplanation;
        purchase.unit = unit;
        purchase.quantity = quantity;
        purchase.unitPrice = unitPrice;
        purchase.price = quantity * unitPrice;
        user.purchases.add(purchase);
        userBox.put(user);

        Balance balance = CalculatorUtil.calculateBalance(purchase, findBalanceByUser(user));
        balanceBox.put(balance);
    }

    public void updatePurchase(User user, PurchaseData purchaseData) {

        Purchase purchaseTobeUpdate = findPurchasesByUser(user)
                .stream()
                .filter(it -> it.id == purchaseData.id)
                .findFirst()
                .orElse(null);

        Balance balance = CalculatorUtil.widthDrawBalance(purchaseTobeUpdate, findBalanceByUser(user));

        String date;
        if(purchaseData.date == null) {
            date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } else {
            date = purchaseData.date;
        }
        purchaseTobeUpdate.date = date;
        purchaseTobeUpdate.productExplanation = purchaseData.productExplanation;
        purchaseTobeUpdate.unit = Unit.findUnit(purchaseData.unit);
        purchaseTobeUpdate.quantity = purchaseData.quantity;
        purchaseTobeUpdate.unitPrice = Double.parseDouble(purchaseData.unitPrice);
        purchaseTobeUpdate.price = purchaseTobeUpdate.quantity * purchaseTobeUpdate.unitPrice;
        purchaseBox.put(purchaseTobeUpdate);

        Balance balanceToBeUpdate = CalculatorUtil.calculateBalance(purchaseTobeUpdate, balance);
        balanceBox.put(balanceToBeUpdate);
    }

    public boolean removePurchase(User user, PurchaseData purchaseData) {
        Purchase purchaseTobeDelete = findPurchasesByUser(user)
                .stream()
                .filter(it -> it.id == purchaseData.id)
                .findFirst()
                .orElse(null);

        boolean ret = purchaseBox.remove(purchaseTobeDelete);
        if(ret) {
            Balance balance = CalculatorUtil.widthDrawBalance(purchaseTobeDelete, findBalanceByUser(user));
            balanceBox.put(balance);
        }

        return ret;
    }

    public void newPayment(User user,
                           String d,
                           String paymentExplanation,
                           PaymentType paymentType,
                           double amount) {

        String date;
        if(d == null) {
            date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } else {
            date = d;
        }
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.date = date;
        transaction.paymentExplanation = paymentExplanation;
        transaction.paymentType = paymentType;
        transaction.amount = amount;

        user.transactions.add(transaction);
        userBox.put(user);

        Balance balance = CalculatorUtil.calculateBalance(transaction, findBalanceByUser(user));
        balanceBox.put(balance);
    }

    public void updatePayment(User user, PaymentTransactionData paymentData) {
        PaymentTransaction paymentTobeUpdate = findPaymentsByUser(user)
                .stream()
                .filter(it -> it.id == paymentData.id)
                .findFirst()
                .orElse(null);

        Balance balance = CalculatorUtil.widthDrawBalance(paymentTobeUpdate, findBalanceByUser(user));

        String date;
        if(paymentData.date == null) {
            date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } else {
            date = paymentData.date;
        }

        paymentTobeUpdate.date = date;
        paymentTobeUpdate.paymentExplanation = paymentData.paymentExplanation;
        paymentTobeUpdate.paymentType = PaymentType.findType(paymentData.paymentType);
        paymentTobeUpdate.amount = Double.parseDouble(paymentData.amount);
        paymentTransactionBox.put(paymentTobeUpdate);

        Balance balanceToBeUpdate = CalculatorUtil.calculateBalance(paymentTobeUpdate, balance);
        balanceBox.put(balanceToBeUpdate);
    }

    public boolean removePayment(User user, PaymentTransactionData paymentData) {
        PaymentTransaction paymentTobeDelete = findPaymentsByUser(user)
                .stream()
                .filter(it -> it.id == paymentData.id)
                .findFirst()
                .orElse(null);

        boolean ret = paymentTransactionBox.remove(paymentTobeDelete);
        if(ret) {
            Balance balance = CalculatorUtil.widthDrawBalance(paymentTobeDelete, findBalanceByUser(user));
            balanceBox.put(balance);
        }

        return ret;
    }

    public void removeAll() {
        userBox.removeAll();
        purchaseBox.removeAll();
        paymentTransactionBox.removeAll();
        balanceBox.removeAll();
    }
}
