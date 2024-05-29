package com.example.lab2_truonghop5;

public class Employee {
    private String id;
    private String FullName;

    private Boolean isManager;

    public Employee(String id, String fullName, Boolean isManager) {
        this.id = id;
        FullName = fullName;
        this.isManager = isManager;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }

    public String getFullName() {
        return FullName;
    }

    public Boolean getManager() {
        return isManager;
    }

    public String getId() {
        return id;
    }
}
