package com.example.jeanpaucar.litorobotapp.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

/**
 * Created by JEANCARLOS on 08/08/2015.
 */
public class TaskIntentService extends IntentService{

    public static final String TAG = "MyPositionIntentService";
    public static final String ACTION_POSITION = "com.example.jean.POSITION";
    public static final String ACTION_POSITION_FIN = "com.example.jean.POSITION_FIN";
    public static final String PROGRESS_POSITION = "progressPsotion";

    public TaskIntentService() {
        super(TAG);
    }
//http://www.sgoliver.net/blog/tareas-en-segundo-plano-en-android-ii-intentservice/
    @Override
    protected void onHandleIntent(Intent intent) {
        /*Log.e(TAG,"Inicio de tarea ...");
        longTask();
        Log.e(TAG,"Fin de tarea ...");*/

        for(int i = 0;i<100;i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intentTestService = new Intent();
            intentTestService.setAction(ACTION_POSITION);
            intentTestService.putExtra(PROGRESS_POSITION,i);
            //intentTestService.addFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
            sendBroadcast(intentTestService);
            Log.e("TASK", "Position");
        }

        Intent intentTestService2 = new Intent();
        intentTestService2.setAction(ACTION_POSITION_FIN);
        intentTestService2.putExtra(PROGRESS_POSITION,123456);
        //intentTestService2.addFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intentTestService2);
        Log.e("TASK", "Fin");

    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    private void longTask(){
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
