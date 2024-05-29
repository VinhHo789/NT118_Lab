package com.example.lab4_bt3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.lab4_bt3.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class AddStudentActivity extends AppCompatActivity {

    private EditText editTextName;

    private DatabaseHandler databaseHandler;

    private EditText editTextAddress;
    private Button buttonDone;
    private StudentAdapter adapter;

    private StudentAddedListener studentAddedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
        buttonDone = findViewById(R.id.buttonDone);
        studentAddedListener = (StudentAddedListener) getParent();
        // Set up the "Done" button click listener
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement the add functionality
                Student newStudent = new Student( 1, editTextName.getText().toString(), editTextAddress.getText().toString());
                databaseHandler = new DatabaseHandler(AddStudentActivity.this);
                // Add the student to the database
                long insertedId = databaseHandler.addStudent(newStudent);
                studentAddedListener.addStudentToList(newStudent);
                if (insertedId != -1) {
                    // Student added successfully to the database
                    setResult(RESULT_OK);
                } else {
                    // Failed to add student to the database
                    setResult(RESULT_CANCELED);
                }

                finish(); // Close the add student activity
            }
        });


    }

}


