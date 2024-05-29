package com.example.lab6_bt1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;
import android.os.BatteryManager;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBroadcastReciever();
    }



    public void processReceive (Context context, Intent intent) {
        Toast.makeText(context, getString(R.string.you_have_a_new_message),Toast.LENGTH_LONG).show();
        TextView tvContent = (TextView) findViewById(R.id.tv_content);
        Bundle bundle = intent.getExtras();
        final String SMS_EXTRA = "pdus";
        Object[] messages = (Object[]) bundle.get(SMS_EXTRA);

        String sms ="";

        SmsMessage smsMsg;
        for (int i = 0; i < messages.length; i++) {
            byte[] pdu = (byte[]) messages[i];
            String format = bundle.getString("format");
            smsMsg = SmsMessage.createFromPdu(pdu, format);

        // Get message body
            String msgBody = smsMsg.getMessageBody();
        // Get source address of message
            String address = smsMsg.getDisplayOriginatingAddress();
            sms += address + "\n" + msgBody + "\n";
        }
        // Show messages in textview
        tvContent.setText(sms);
    }

    private void initBroadcastReciever() {
        filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);

        broadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() != null && intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                    processReceive(context, intent);
                }
            }
        };

        // Register the BatteryReceiver
        registerReceiver(new BatteryReceiver(), filter);
    }
    public void processBatteryStatus(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

        if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
            Toast.makeText(context, "Battery is charging", Toast.LENGTH_SHORT).show();
        } else if (status == BatteryManager.BATTERY_STATUS_FULL) {
            Toast.makeText(context, "Battery is fully charged", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (broadcastReceiver == null) initBroadcastReciever();

        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop(){
        super.onStop();

        unregisterReceiver(broadcastReceiver);
    }
}