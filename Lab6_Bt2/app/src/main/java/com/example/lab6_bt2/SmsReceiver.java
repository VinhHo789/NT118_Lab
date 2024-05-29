package com.example.lab6_bt2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.ArrayList;


public class SmsReceiver extends BroadcastReceiver {

    public static final String SMS_FORWARD_BROADCAST_RECEIVER = "sms_forward_broadcast_receiver";
    public static final String SMS_MESSAGE_ADDRESS_KEY = "sms_messages_key";

    @Override
    public void onReceive(Context context, Intent intent) {
        String queryString = "Are you OK?";
        queryString.toLowerCase();
        Log.d("SmsReceiver", "Received SMS broadcast");

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus != null) {
                Log.d("SmsReceiver", "Number of messages: " + pdus.length);
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    byte[] pdu = (byte[]) pdus[i];
                    String format = bundle.getString("format");
                    messages[i] = SmsMessage.createFromPdu(pdu, format);

                    if (messages[i] != null) {
                        Log.d("SmsReceiver", "Message " + i + " is not null");
                        Log.d("SmsReceiver", "Message " + i + ": " + messages[i].getMessageBody());
                    } else {
                        Log.d("SmsReceiver", "Message " + i + " is null");
                    }
                }

                // Create ArrayList of OriginatingAddress of messages which contain queryString
                ArrayList<String> addresses = new ArrayList<>();
                for (SmsMessage message : messages) {
                    if (message != null) {
                        String messageBody = message.getDisplayMessageBody();
                        if (messageBody != null && messageBody.toLowerCase().contains(queryString.toLowerCase())) {
                            addresses.add(message.getOriginatingAddress());
                            Log.d("MessageAddress", message.getOriginatingAddress());
                        }
                    }
                }


                if (!addresses.isEmpty()) {
                    if (!MainActivity.isRunning) {
                        // Start MainActivity if it's not running
                        Log.d("SOS", "Main activity is not running");
                        Intent iMain = new Intent(context, MainActivity.class);
                        iMain.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, addresses);
                        iMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.sendBroadcast(iMain);
                        context.startActivity(iMain);

                    } else {
                        Log.d("SmsReceiver", "Forwarding addresses to MainActivity: " + addresses.toString());
                        // Forward these addresses to MainActivity to process
                        Intent iForwardBroadcastReceiver = new Intent(SMS_FORWARD_BROADCAST_RECEIVER);
                        iForwardBroadcastReceiver.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, addresses);
                        context.sendBroadcast(iForwardBroadcastReceiver);
                    }
                }

            } else {
                Log.d("SmsReceiver", "pdus is null");
            }
        }
    }
}
