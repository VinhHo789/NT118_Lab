package com.example.lab6_bt1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import android.os.BatteryManager;

public class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)) {
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                Toast.makeText(context, "Battery is charging", Toast.LENGTH_SHORT).show();
            } else if (status == BatteryManager.BATTERY_STATUS_FULL) {
                Toast.makeText(context, "Battery is fully charged", Toast.LENGTH_SHORT).show();
            } else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING ||
                    status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
                Toast.makeText(context, "Battery is not charging", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

