package com.example.weektwotest.ui.main;

public class PhoneNumber {
    private String name;
    private String number;
    private String email;

    public PhoneNumber (String name, String number) {
        this.name = name;
        this.number = number;
        this.email = "";
    }

    public PhoneNumber (String name, String number, String email){
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() { return email; }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number){this.number = number;}

    public void setEmail(String email){this.email = email;}
}
