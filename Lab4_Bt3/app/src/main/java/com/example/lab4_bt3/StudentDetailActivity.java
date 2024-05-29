package com.example.lab4_bt3;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

// StudentAdapter.java
public class StudentDetailActivity extends AppCompatActivity {

    private EditText editTextName;
    private DatabaseHandler databaseHandler;
    private StudentDeletedListener studentDeletedListener;
    private EditText editTextAddress;
    private EditText editTextId;
    private Button buttonEdit;
    private Button buttonDelete;
    private Button buttonDone;
    private StudentAddedListener studentAddedListener;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        // Retrieve the student object
        student = getIntent().getParcelableExtra("student");
        studentDeletedListener = (StudentDeletedListener) getIntent().getSerializableExtra("studentDeletedListener");

        // Initialize UI components
        editTextId = findViewById(R.id.editTextStudentId);
        editTextName = findViewById(R.id.editTextStudentName);
        editTextAddress = findViewById(R.id.editTextStudentAddress);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonDone = findViewById(R.id.buttonDone);

        // Populate UI with student details
        Log.d("StudentDebug", "ID: " + student.getId());
        Log.d("StudentDebug", "Name: " + student.getName());
        Log.d("StudentDebug", "Address: " + student.getAddress());

        editTextId.setText(String.valueOf(student.getId()));
        editTextName.setText(student.getName());
        editTextAddress.setText(student.getAddress());

        editTextId.setText(String.valueOf(student.getId()));
        editTextName.setText(student.getName());
        editTextAddress.setText(student.getAddress());
        editTextAddress.setEnabled(false);
        editTextName.setEnabled(false);
        editTextId.setEnabled(false);

        // Set up the "Edit" button click listener
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextName.setEnabled(true);
                editTextAddress.setEnabled(true);



            }
        });
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHandler = new DatabaseHandler(StudentDetailActivity.this);
                // Enable editing by switching TextViews to EditTexts
                String editedName = editTextName.getText().toString();
                String editedAddress = editTextAddress.getText().toString();

                // Update the corresponding Student object
                student.setName(editedName);
                student.setAddress(editedAddress);

                // Save the changes to the database
                databaseHandler.updateStudent(student);
                studentAddedListener.updateStudentToList();
                finish();


            }
        });

        // Set up the "Delete" button click listener
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement the delete functionality
                // For simplicity, you can show a confirmation dialog and delete the student here

                showDeleteConfirmationDialog();



            }
        });
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this student?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Delete the student and finish the activity
                        deleteStudent();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog, do nothing
                    }
                });
        builder.create().show();
    }

    private void deleteStudent() {
        // Implement the logic to delete the student (e.g., use your DatabaseHandler)
        DatabaseHandler db = new DatabaseHandler(this);
        db.deleteStudent(student);
        db.close();
        studentDeletedListener.onStudentDeleted();

    }
}

