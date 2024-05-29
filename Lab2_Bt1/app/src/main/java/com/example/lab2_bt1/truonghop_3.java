package com.example.lab2_bt1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class truonghop_3 extends AppCompatActivity {

    private EditText editText;
    private Button addButton;
    private ListView listView;
    private TextView textViewSelection;
    private ArrayList<String> dataArray;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truonghop_3);

        editText = findViewById(R.id.edit_text);
        addButton = findViewById(R.id.button_add);
        listView = findViewById(R.id.list_view);
        textViewSelection = findViewById(R.id.text_view_selection);

        dataArray = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataArray);
        listView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if (!text.isEmpty()) {
                    dataArray.add(text);
                    adapter.notifyDataSetChanged();
                    editText.setText("");
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedText = "Position: " + position + "; Value: " + dataArray.get(position);
                textViewSelection.setText(selectedText);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dataArray.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }
}

