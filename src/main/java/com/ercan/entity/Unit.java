package com.ercan.entity;

import io.objectbox.converter.PropertyConverter;

import java.util.Arrays;

public enum Unit {
    DEFAUTL(""),
    ADET("ADET"),
    M("METRE"),
    M2("METREKARE"),
    KG("KILO"),
    M3("METREKUP"),
    H("SAAT"),
    KM("KM"),
    DIGER("DIGER");

    Unit(String name){
        this.name = name;
    }
    Unit(){}
    private String name;

    public String getName() {
        return name;
    }

    public static Unit findUnit(String name) {
        return Arrays.stream(values()).filter(unit -> unit.name.equals(name)).findFirst().orElse(DIGER);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "name='" + name + '\'' +
                '}';
    }

    static class UnitConverter implements PropertyConverter<Unit, String> {

        @Override
        public Unit convertToEntityProperty(String databaseValue) {
            if(databaseValue == null) return null;
            for(Unit unit: Unit.values()) {
                if(unit.name.equals(databaseValue)) return unit;
            }
            return Unit.DEFAUTL;
        }

        @Override
        public String convertToDatabaseValue(Unit entityProperty) {
            return entityProperty == null ? null : entityProperty.name;
        }
    }
}


