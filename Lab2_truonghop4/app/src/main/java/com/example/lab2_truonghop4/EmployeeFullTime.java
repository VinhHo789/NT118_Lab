package com.example.lab2_truonghop4;

public class EmployeeFullTime extends Employee{

    public EmployeeFullTime () {
        super();
    }

    public EmployeeFullTime(String id, String name) {
        super(id, name);
    }


    @Override
    public String toString() {
        return super.toString() + "Fulltime= " + tinhLuong();
    }

    @Override
    public double tinhLuong(){
        return 500.0;
    }
}
