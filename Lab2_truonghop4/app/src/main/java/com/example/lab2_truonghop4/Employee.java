package com.example.lab2_truonghop4;

public class Employee {
    private String id;
    private String name;

    // Constructors
    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Employee() {
        this.id = null;
        this.name = null;
    }

    // Getter and Setter methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // toString met
    public String toString() {

        return this.id + " - " + this.name + " -->";

    }

    public double tinhLuong(){
        return 0;
    }
}
