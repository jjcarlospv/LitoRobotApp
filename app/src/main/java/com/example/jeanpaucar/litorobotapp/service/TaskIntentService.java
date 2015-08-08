package com.example.jeanpaucar.litorobotapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by JEANCARLOS on 08/08/2015.
 */
public class TaskIntentService extends IntentService{

    static final String TAG = "MyIntentService";

    public TaskIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG,"Inicio de tarea ...");
        longTask();
        Log.e(TAG,"Fin de tarea ...");
    }

    private void longTask(){
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
