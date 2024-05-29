package com.example.lab6_bt2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


public class MainActivity extends AppCompatActivity {

    private ReentrantLock reentrantLock;
    private Switch swAutoResponse;
    private LinearLayout llButtons;
    private Button btnSafe, btnMayday;
    private ArrayList<String> requesters;
    private ArrayAdapter<String> adapter;
    private ListView lvMessages;
    private BroadcastReceiver broadcastReceiver;
    public static boolean isRunning;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private final String AUTO_RESPONSE = "auto_response";
    private static final int SMS_PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsByIds();
        initVariables();


        // Move the rest of the initialization here
        handleOnClickListenner();



        String action = getIntent().getAction();
        Log.e("CheckIntent", "Action: " + action);
        if (action != null && action.equals(SmsReceiver.SMS_FORWARD_BROADCAST_RECEIVER)) {
            Log.d("CheckIntent", "This is a SMS forward broadcast receiver intent");
        }
    }








    private void findViewsByIds() {
        swAutoResponse = (Switch) findViewById(R.id.sw_auto_response);
        llButtons = (LinearLayout) findViewById(R.id.ll_buttons);
        lvMessages = (ListView) findViewById(R.id.lv_messages);
        btnSafe = (Button) findViewById(R.id.btn_safe);
        btnMayday = (Button) findViewById(R.id.btn_mayday);
    }

    private void respond (String to, String response){
        reentrantLock.lock();
        requesters.remove(to);
        adapter.notifyDataSetChanged();
        reentrantLock.unlock();

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(to, null, response, null, null);

    }

    public void respond(boolean ok){
        String okString = getString(R.string.i_am_safe_and_well_worry_not);
        String notOkString = getString(R.string.tell_my_mother_i_love_her);
        String outputString = ok? okString : notOkString;
        ArrayList<String> requestersCopy = (ArrayList<String>) requesters.clone();

        for (String to : requestersCopy)
            respond(to, outputString);
    }

    public void processReceiveAddresses(ArrayList<String> addresses) {
        for (String address : addresses) {
            if (!requesters.contains(address)) {
                reentrantLock.lock();
                requesters.add(address);
                reentrantLock.unlock();
            }
        }

        Log.d("MyApp", "requesters size: " + requesters.size() + "/" + requesters.toString());

        // Check to response automatically
        if (swAutoResponse.isChecked()) {
            respond(true);
        }

        // Notify the adapter that the data set has changed
        adapter.notifyDataSetChanged();
    }



    private void handleOnClickListenner() {
        // Handle onClickListenner
        btnSafe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                respond(true);
            }
        });
        btnMayday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                respond(false);
            }
        });

        swAutoResponse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) llButtons.setVisibility(View.GONE);
                else llButtons.setVisibility(View.VISIBLE);

                editor.putBoolean(AUTO_RESPONSE, isChecked);
                editor.commit();
            }
        });
    }



    private void initBroadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<String> addresses = intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY);
                processReceiveAddresses(addresses);
                adapter.notifyDataSetChanged();
            }


        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        // Check for incoming addresses when the MainActivity is resumed


        if (broadcastReceiver == null) {
            initBroadcastReceiver();
        }
        IntentFilter intentFilter = new IntentFilter(SmsReceiver.SMS_FORWARD_BROADCAST_RECEIVER);
        registerReceiver(broadcastReceiver, intentFilter);

    }


    @Override
    protected void onStop() {
        Log.d("OnStop", "App is stopping");
        super.onStop();
        isRunning = false;
        unregisterReceiver(broadcastReceiver);

        //restartApp();
    }

    @Override
    protected void onDestroy() {
        Log.d("OnDestroy", "App has been destroyed");
        super.onDestroy();
        isRunning = false;

    }





    private void initVariables () {
        sharedPreferences = getPreferences (MODE_PRIVATE);
        editor = sharedPreferences.edit();
        reentrantLock = new ReentrantLock();
        requesters = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, requesters);
        lvMessages.setAdapter (adapter);
        // Load auto response setting
        boolean autoResponse = sharedPreferences.
                getBoolean (AUTO_RESPONSE, false);
        swAutoResponse.setChecked (autoResponse);
        if (autoResponse) llButtons.setVisibility (View.GONE);

        initBroadcastReceiver();
        try {
            ArrayList<String> addresses = getIntent().getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY);
            processReceiveAddresses(addresses);
        } catch (Exception e) {
            // This will catch any exception, because they are all descended from Exception
            System.out.println("Error " + e.getMessage());

        }
    }




}


