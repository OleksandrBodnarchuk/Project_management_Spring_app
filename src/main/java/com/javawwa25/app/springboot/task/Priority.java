package com.javawwa25.app.springboot.task;

public enum Priority {
    LOW("Low"),
    NORMAL("Normal"),
    MEDIUM("Medium"),
    HIGH("High"),
    URGENT("Urgent");

    private final String displayValue;

    private Priority(String displayValue) {
        this.displayValue = displayValue;
    }

    public String value() {
        return displayValue;
    }
}
