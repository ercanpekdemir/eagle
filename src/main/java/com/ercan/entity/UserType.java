package com.ercan.entity;

import io.objectbox.converter.PropertyConverter;

public enum UserType {
    DEFAULT(""),
    CUSTOMER("M"),
    FIRM("F");

    private String type;
    UserType(String type) {
        this.type = type;
    }
    UserType(){}
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    static class UserTypeConverter implements PropertyConverter<UserType, String> {

        @Override
        public UserType convertToEntityProperty(String databaseValue) {
            if(databaseValue == null) return null;
            for(UserType userType: UserType.values()) {
                if(userType.type.equals(databaseValue)) return userType;
            }
            return UserType.DEFAULT;
        }

        @Override
        public String convertToDatabaseValue(UserType userType) {
            return userType == null ? null : userType.type;
        }
    }
}
