package com.example.lab2_truonghop6;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import com.example.lab2_truonghop5.R;

public class MainActivity extends Activity {
    private EditText idEditText;
    private EditText FullNameEditText;
    private CheckBox checkbox;
    private List<Employee> employees;
    private EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idEditText = findViewById(R.id.id);
        FullNameEditText = findViewById(R.id.fullname);
        checkbox = findViewById(R.id.checkbox);

        employees = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EmployeeAdapter(this, employees);
        recyclerView.setAdapter(adapter);

        Button addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEmployeeToList();
            }
        });
    }

    private void addEmployeeToList() {
        String id = idEditText.getText().toString();
        String name = FullNameEditText.getText().toString();

        Employee employee;

        if (checkbox.isChecked()) {
            employee = new Employee(id, name, true);
        } else {
            employee = new Employee(id, name, false);
        }

        employees.add(employee);
        adapter.notifyItemInserted(employees.size() - 1);

        idEditText.setText("");
        FullNameEditText.setText("");
        checkbox.setChecked(false);
    }
}
