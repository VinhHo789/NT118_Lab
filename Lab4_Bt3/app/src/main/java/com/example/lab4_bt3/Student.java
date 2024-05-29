package com.example.lab4_bt3;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;

public class Student implements Parcelable {
    private int id;
    private String name;
    private String address;

    public Student(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public Student(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
// Constructors, getters, setters...

    // Parcelable implementation
    protected Student(Parcel in) {
        id = in.readInt();
        name = in.readString();
        address = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(address);
    }
}

