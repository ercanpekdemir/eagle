package com.ercan.repo;

import com.ercan.entity.*;
import com.ercan.tabledata.PaymentTransactionData;
import com.ercan.tabledata.PurchaseData;
import com.ercan.tabledata.UserData;
import io.objectbox.exception.UniqueViolationException;
import io.objectbox.query.QueryBuilder;

import java.util.Date;
import java.util.List;

public class Repository {
    private DataSource dataSource;
    public Repository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean newUser(String name, String tel, String address, UserType userType) {
        return dataSource.newUser(name, tel, address, userType);
    }

    public User findUser(String name, UserType userType) {
        return dataSource.findUser(name, userType);
    }

    public boolean removeUser(User user) {
        return dataSource.removeUser(user);
    }

    public List<User> searchUser(String key, UserType userType) {
        return dataSource.searchUser(key, userType);
    }

    public void updateUser(User user, UserData userData) {
        dataSource.updateUser(user, userData);
    }

    public boolean isUserExist(User user) {
        return dataSource.isUserExist(user);
    }

    public Balance findBalanceByUser(User user) {
        return dataSource.findBalanceByUser(user);
    }

    public List<Purchase> findPurchasesByUser(User user) {
        return dataSource.findPurchasesByUser(user);
    }

    public List<PaymentTransaction> findPaymentsByUser(User user) {
        return dataSource.findPaymentsByUser(user);
    }

    public void newPurchase(User user,
                            String date,
                            String productExplanation,
                            Unit unit,
                            double quantity,
                            double unitPrice) {
        dataSource.newPurchase(user, date, productExplanation, unit, quantity, unitPrice);
    }

    public void updatePurchase(User user, PurchaseData purchaseData) {
        dataSource.updatePurchase(user, purchaseData);
    }

    public boolean removePurchase(User user, PurchaseData purchaseData) {
        return dataSource.removePurchase(user, purchaseData);
    }

    public void newPayment(User user,
                           String date,
                           String paymentExplanation,
                           PaymentType paymentType,
                           double amount) {
        dataSource.newPayment(user, date, paymentExplanation, paymentType, amount);
    }

    public void updatePayment(User user, PaymentTransactionData paymentData) {
        dataSource.updatePayment(user, paymentData);
    }

    public boolean removePayment(User user, PaymentTransactionData paymentData) {
        return dataSource.removePayment(user, paymentData);
    }

    public boolean newProduct(String product) {
        return dataSource.newProduct(product);
    }

    public boolean removeProduct(String product) {
        return dataSource.removeProduct(product);
    }

    public List<Product> getProducts() {
        return dataSource.getProducts();
    }

    public List<Product> findProductByName(String product) {
        return dataSource.findProductByName(product);
    }

    public void removeAll() {
        dataSource.removeAll();
    }



}
