package com.example.lab4_bt2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Contact> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);

        listView = findViewById(R.id.listViewContacts);

        new DatabaseOperationTask().execute();

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        //db.addContact(new Contact("Ravi", "9100000000"));
        //db.addContact(new Contact("Srinivas", "9199999999"));
        //db.addContact(new Contact("Tommy", "9522222222"));
        //db.addContact(new Contact("Karthik", "9533333333"));

        // Reading all contacts
        Log.e("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: " + cn.getId() + " ,Name: " + cn.getName() +
                    ",Phone: " + cn.getPhoneNumber();
            // Writing Contacts to log
            Log.e("Name: ", log);
        }
    }

    private class DatabaseOperationTask extends AsyncTask<Void, Void, List<Contact>> {

        @Override
        protected List<Contact> doInBackground(Void... params) {
            DatabaseHandler db = new DatabaseHandler(MainActivity.this);
            return db.getAllContacts();
        }

        @Override
        protected void onPostExecute(List<Contact> contacts) {
            super.onPostExecute(contacts);

            // Create an adapter to display the contacts in the ListView
            adapter = new ArrayAdapter<>(MainActivity.this, R.layout.list_item_contact, contacts);

            // Set the adapter to the ListView
            listView.setAdapter(adapter);

            // Set the long click listener to delete a contact on long press
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the selected contact
                    Contact selectedContact = adapter.getItem(position);

                    // Delete the contact from the database
                    new DeleteContactTask().execute(selectedContact);

                    return true; // consume the long click
                }
            });
        }
    }

    private class DeleteContactTask extends AsyncTask<Contact, Void, Void> {

        @Override
        protected Void doInBackground(Contact... contacts) {
            DatabaseHandler db = new DatabaseHandler(MainActivity.this);
            Contact contactToDelete = contacts[0];
            db.deleteContact(contactToDelete);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            // Refresh the ListView after deletion
            new DatabaseOperationTask().execute();
        }
    }
}