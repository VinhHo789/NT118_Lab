package com.example.sharedprecontentpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.graphics.Color;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // The ListView
    private ListView lstNames;

    // Request code for READ_CONTACTS. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contentprovider);

        // Find the list view
        this.lstNames = (ListView) findViewById(R.id.listView);

        // Read and show the contacts
        showContacts();
    }

    /**
     * Show the contacts in the ListView.
     */
    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            List<String> contacts = getContactNames();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
            lstNames.setAdapter(adapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Read the name of all the contacts.
     *
     * @return a list of names.
     */
    private List<String> getContactNames() {
        List<String> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(name);
            } while (cursor.moveToNext());
        }
        // Close the curosor
        cursor.close();

        return contacts;
    }
}


//class StyleGenerator {
//    public static int getRandomTextSize() {
//        // Generate a random text size between 12 and 36 (adjust range as needed)
//        return (int) (Math.random() * 25) + 12;
//    }
//
//    public static int getRandomTextColor() {
//        // Generate a random color
//        return Color.rgb(
//                (int) (Math.random() * 256),
//                (int) (Math.random() * 256),
//                (int) (Math.random() * 256)
//        );
//    }
//
//    public static int getRandomBackgroundColor() {
//        // Generate a random color
//        return Color.rgb(
//                (int) (Math.random() * 256),
//                (int) (Math.random() * 256),
//                (int) (Math.random() * 256)
//        );
//    }
//}
//
//public class MainActivity extends AppCompatActivity {
//
//    SharedPreferences sharedPreferences;
//    public TextView textView;
//
//    // ...
//
//    private void saveRandomStyles() {
//        int textSize = StyleGenerator.getRandomTextSize();
//        int textColor = StyleGenerator.getRandomTextColor();
//        int backgroundColor = StyleGenerator.getRandomBackgroundColor();
//
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        editor.putInt("text_size", textSize);
//        editor.putInt("text_color", textColor);
//        editor.putInt("background_color", backgroundColor);
//
//        editor.apply();
//    }
//
//    private void applySavedStyles() {
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//
//        int textSize = sharedPreferences.getInt("text_size", 16); // Default size if not found
//        int textColor = sharedPreferences.getInt("text_color", Color.BLACK); // Default color if not found
//        int backgroundColor = sharedPreferences.getInt("background_color", Color.WHITE); // Default color if not found
//
//        // Apply styles to your UI elements
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
//        textView.setTextColor(textColor);
//        textView.setBackgroundColor(backgroundColor);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        textView = findViewById(R.id.textView);
//        //for(int i = 0; i<15; i++){
//            saveRandomStyles(); // tạo 15 styles khác nhau
//       // }
//        applySavedStyles();
//    }
//}