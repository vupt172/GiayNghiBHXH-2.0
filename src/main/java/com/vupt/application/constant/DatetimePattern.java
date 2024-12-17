package com.vupt.application.constant;

public enum DatetimePattern {
    DATE0("dd/MM/yyyy"),
    DATE1("yyyyMMdd"),
    DATETIME0("dd/MM/yyyy hh:mm:ss"),
    DATETIME1("yyyyMMddHHmm"),
    DATETIME2("yyyyMMddHHmmSS");


    private String pattern;

    DatetimePattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
