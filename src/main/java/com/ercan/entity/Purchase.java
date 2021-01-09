package com.ercan.entity;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;


@Entity
public class Purchase {
    @Id
    public long id;
    public String date;
    public String productExplanation;
    @Convert(converter = Unit.UnitConverter.class, dbType = String.class)
    public Unit unit;
    public double quantity;
    public double unitPrice;
    public double price;

    public ToOne<User> user = new ToOne<>(this, com.ercan.entity.Purchase_.user);

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

    public String getProductExplanation() {
        return productExplanation;
    }

    public void setProductExplanation(String productExplanation) {
        this.productExplanation = productExplanation;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ToOne<User> getUser() {
        return user;
    }

    public void setUser(ToOne<User> user) {
        this.user = user;
    }


    public Purchase(){}
    public Purchase(long id, String date, String productExplanation, Unit unit, double quantity, double unitPrice, double price, ToOne<User> user) {
        this.id = id;
        this.date = date;
        this.productExplanation = productExplanation;
        this.unit = unit;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.price = price;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", date=" + date +
                ", productExplanation='" + productExplanation +
                ", unit=" + unit.toString() +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", price=" + price +
                ", user=" + user.getTarget().name +
                '}';
    }
}
