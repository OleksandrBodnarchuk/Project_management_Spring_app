package com.javawwa25.app.springboot.models;

public enum Progress {

    TODO("To Do"),
    IN_PROGRESS ("In progress"),
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
