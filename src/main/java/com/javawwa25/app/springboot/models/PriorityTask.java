package com.javawwa25.app.springboot.models;

public enum PriorityTask {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");

    private final String displayValue;

    private PriorityTask(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
