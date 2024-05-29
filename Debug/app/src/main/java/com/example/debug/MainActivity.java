package com.example.debug;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    AppCompatActivity activity = new AppCompatActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "The activity is created");
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "The activity is visible and about to be started");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "The activity is visible and about to be restarted");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "The activity is and has focus (it is now \"resumed\")");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "The activity is and has focus (it is now \"resumed\")");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "The activity is and has focus (it is now \"resumed\")");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "The activity is and has focus (it is now \"resumed\")");
    }



}