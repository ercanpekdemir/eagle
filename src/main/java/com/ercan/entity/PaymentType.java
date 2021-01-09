package com.ercan.entity;

import io.objectbox.converter.PropertyConverter;

import java.util.Arrays;

public enum PaymentType {
    DEFAULT(""),
    CC("KART"),
    CASH("NAKIT"),
    CEK("CEK"),
    RETURN("IADE"),
    DIGER("DIGER");

    PaymentType(String type) {
        this.type = type;
    }
    PaymentType(){}
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static PaymentType findType(String type) {
        return Arrays.stream(values()).filter(item -> item.type.equals(type)).findFirst().orElse(DIGER);
    }

    @Override
    public String toString() {
        return "PaymentType{" +
                "type='" + type + '\'' +
                '}';
    }

    static class PaymentTypeConverter implements PropertyConverter<PaymentType, String> {

        @Override
        public PaymentType convertToEntityProperty(String databaseValue) {
            if(databaseValue == null) return null;
            for(PaymentType paymentType: PaymentType.values()) {
                if(paymentType.type.equals(databaseValue)) return paymentType;
            }
            return PaymentType.DEFAULT;
        }

        @Override
        public String convertToDatabaseValue(PaymentType paymentType) {
            return paymentType == null ? null : paymentType.type;
        }
    }
}
