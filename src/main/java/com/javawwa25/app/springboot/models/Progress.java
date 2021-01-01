package com.javawwa25.app.springboot.models;

public enum Progress {

    TODO("To_Do"),
    IN_PROGRESS ("In_progress"),
    QA ("QA"),
    DONE("Done");

    private final String displayValue;

    private Progress(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
