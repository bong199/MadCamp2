package com.example.weektwotest.ui.main;

public class Student {
    private String Id;
    private String firstName;

    public Student(String Id, String firstName) {
        this.Id = Id;
        this.firstName = firstName;
    }

    public String getId() {
        return Id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setId(String Id) { this.Id = Id; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}