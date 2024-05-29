package com.example.lab4_bt3;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "studentManager";
    private static final String TABLE_STUDENTS = "students";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," // Specify AUTOINCREMENT here
                + KEY_NAME + " TEXT,"
                + KEY_ADDRESS + " TEXT" + ")";
        db.execSQL(CREATE_STUDENTS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    public long addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());
        values.put(KEY_ADDRESS, student.getAddress());

        // Insert the values into the database
        long insertedId = db.insert(TABLE_STUDENTS, null, values);

        db.close();

        return insertedId; // Return the inserted ID, or -1 if there was an error
    }


    @SuppressLint("Range")
    public Student getStudent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENTS, new String[]{KEY_ID, KEY_NAME, KEY_ADDRESS},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            @SuppressLint("Range") Student student = new Student(
                    cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                    cursor.getString(cursor.getColumnIndex(KEY_ADDRESS))
            );
            student.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            cursor.close();
            return student;
        }
        return null;
    }

    @SuppressLint("Range")
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Student student = new Student(
                        cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                        cursor.getString(cursor.getColumnIndex(KEY_ADDRESS))
                );
                student.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return studentList;
    }

    public int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());
        values.put(KEY_ADDRESS, student.getAddress());
        return db.update(TABLE_STUDENTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(student.getId())});
    }

    public int deleteStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_STUDENTS, KEY_ID + " = ?",
                new String[]{String.valueOf(student.getId())});
        db.close();
        return rowsAffected;
    }

}
