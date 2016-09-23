package com.example.jeanpaucar.litorobotapp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by JEANCARLOS on 18/08/2015.
 */
public class MyBroadcastReceiverLocation extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Broadacast_Class", intent.getAction());
    }
}
