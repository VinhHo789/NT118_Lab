package com.example.lab2_gridview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private EditText nameEditText;
    private CheckBox checkbox;
    private GridView gridView;
    private Spinner spinner;
    private ThumbnailAdapter thumbnailAdapter;
    private DishAdapter dishAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        nameEditText = findViewById(R.id.name);
        checkbox = findViewById(R.id.checkbox);
        gridView = findViewById(R.id.grid_view);
        spinner = findViewById(R.id.spinner);

        // Initialize adapters
        List<Thumbnail> thumbnails = new ArrayList<>();
        thumbnails.add(Thumbnail.Thumbnail1);
        thumbnails.add(Thumbnail.Thumbnail2);
        thumbnails.add(Thumbnail.Thumbnail3);
        thumbnails.add(Thumbnail.Thumbnail4);

        thumbnailAdapter = new ThumbnailAdapter(this, thumbnails);
        spinner.setAdapter(thumbnailAdapter);

        List<Dish> dishes = new ArrayList<>();
        dishAdapter = new DishAdapter(this, dishes);
        gridView.setAdapter(dishAdapter);

        // Set up add button click listener
        Button addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDishToList();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Thumbnail selectedThumbnail = (Thumbnail) adapterView.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "Selected Thumbnail: " + selectedThumbnail.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });
    }

    private void addDishToList() {
        // Get user input
        String name = nameEditText.getText().toString();
        boolean hasPromotion = checkbox.isChecked();
        Thumbnail selectedThumbnail = (Thumbnail) spinner.getSelectedItem();

        // Create a new Dish
        Dish newDish = new Dish(name, selectedThumbnail.getImageResource(), hasPromotion);

        // Add the new Dish to the list
        dishAdapter.add(newDish);

        // Notify the adapter of the data change
        dishAdapter.notifyDataSetChanged();

        // Show a toast message
        Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();

        // Reset input fields
        nameEditText.setText("");
        checkbox.setChecked(false);
        spinner.setSelection(0); // Reset spinner to first item
    }
}
