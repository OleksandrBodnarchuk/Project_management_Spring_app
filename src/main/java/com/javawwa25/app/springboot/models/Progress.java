package com.javawwa25.app.springboot.models;

public enum Progress {
	
	NEW("New"),
	ANALYSIS("Analysis"),
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
