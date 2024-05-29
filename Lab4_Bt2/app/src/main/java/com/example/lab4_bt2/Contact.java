package com.example.lab4_bt2;

import androidx.annotation.NonNull;

public class Contact {
    private int id;
    private String name;
    private String phoneNumber;

    public Contact(String name, String phone) {
        this.name = name;
        this.phoneNumber = phone;
    }

    public Contact() {
        this.name = "";
        this.phoneNumber = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber;
    }
}
