package com.example.lab4_bt3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

// MainActivity.java
public class MainActivity extends AppCompatActivity implements StudentAddedListener, StudentDeletedListener {

    private List<Student> studentList;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;

    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewStudents);
        studentList = new ArrayList<>();
        db = new DatabaseHandler(this);
        studentList = db.getAllStudents();
        // Initialize RecyclerView and adapter
        adapter = new StudentAdapter(studentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Populate studentList (you can load it from the database)
        populateStudentList();

        // Set item click listener to open StudentDetailActivity
        adapter.setOnItemClickListener(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this, StudentDetailActivity.class);
                intent.putExtra("student", studentList.get(position));
                intent.putExtra("studentDeletedListener", studentList.get(position));
                startActivity(intent);
            }
        });



        // Set item long click listener to delete a student
        adapter.setOnItemLongClickListener(new StudentAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                // Delete the student from the list and update the adapter
                studentList.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        // Set up "Add Student" button click listener
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                startActivityForResult(intent, 1); // Request code 1 for adding a student
            }
        });
    }

    public void addStudentToList(Student student) {
        studentList.add(student);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
    public void updateStudentToList() {
        studentList.clear();
        studentList = db.getAllStudents();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onStudentDeleted() {
        // Refresh the studentList from the database
        studentList.clear(); // Clear existing data
        studentList.addAll(db.getAllStudents()); // Load updated data from the database
        adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
    }
    private void populateStudentList() {
        // Populate studentList with dummy data (you can load it from the database)
        studentList = db.getAllStudents();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Refresh the studentList from the database
            studentList.clear(); // Clear existing data
            studentList.addAll(db.getAllStudents()); // Load updated data from the database
            adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
        }
    }

}
