package com.example.lab2_truonghop4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private EditText maNvEditText;
    private EditText tenNvEditText;
    private RadioGroup radioGroup;
    private List<Employee> employees;
    private ArrayAdapter<Employee> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        maNvEditText = findViewById(R.id.ma_nv);
        tenNvEditText = findViewById(R.id.ten_nv);
        radioGroup = findViewById(R.id.radioGroup);

        employees = new ArrayList<>();
        ListView listView = findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employees);
        listView.setAdapter(adapter);

        Button addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEmployeeToList();
            }
        });
    }

    private void addEmployeeToList() {
        int selectedRadioId = radioGroup.getCheckedRadioButtonId();

        if (selectedRadioId == -1) {
            Toast.makeText(this, "Please select an employee type", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton radioButton = findViewById(selectedRadioId);

        String id = maNvEditText.getText().toString();
        String name = tenNvEditText.getText().toString();




        Employee employee = new Employee();

        if (radioButton.getId() == R.id.rd_chinhthuc) {
            employee = new EmployeeFullTime(id, name);
        } else {
            employee = new EmployeePartTime(id, name);
        }

        employees.add(employee);
        adapter.notifyDataSetChanged();

        // Clear input fields
        maNvEditText.setText("");
        tenNvEditText.setText("");
        radioGroup.clearCheck();
    }
}
