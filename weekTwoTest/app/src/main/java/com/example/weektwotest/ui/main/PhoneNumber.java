package com.example.weektwotest.ui.main;

public class PhoneNumber {
    private String name;
    private String number;

    public PhoneNumber (String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }
}
