package com.example.lab2_truonghop4;

public class EmployeePartTime extends Employee{
    public EmployeePartTime(String id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return super.toString() + "Parttime= " + tinhLuong();
    }

    public EmployeePartTime () {
        super();
    }
    @Override
    public double tinhLuong(){
        return 150.0;
    }
}
