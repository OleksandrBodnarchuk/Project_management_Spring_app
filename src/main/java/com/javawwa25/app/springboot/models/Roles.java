package com.javawwa25.app.springboot.models;

public enum Roles {

    ADMIN("ADMIN"),
    USER("USER");

    private final String displayValue;

    private Roles(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
