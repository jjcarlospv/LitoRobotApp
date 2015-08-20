package com.example.jeanpaucar.litorobotapp.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.util.Log;

/**
 * Created by JEANCARLOS on 08/08/2015.
 */
public class TaskIntentService extends IntentService{

    public static final String TAG = "MyPositionIntentService";
    public static final String POSITION_ACTION = "com.example.jean.POSITION";
    public static final String POSITION_FIN_ACTION = "com.example.jean.POSITION_FIN";
    public static final String PROGRESS_POSITION = "progressPosition";
    public static final String PROGRESS_POSITION_FIN = "progressPositionFinal";

    public LocationManager locationManager;

    public TaskIntentService() {
        super(TAG);
    }
//http://www.sgoliver.net/blog/tareas-en-segundo-plano-en-android-ii-intentservice/
    @Override
    protected void onHandleIntent(Intent intent) {
        /*Log.e(TAG,"Inicio de tarea ...");
        longTask();
        Log.e(TAG,"Fin de tarea ...");*/


        for(int i = 0;i<200;i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intentTestService = new Intent(POSITION_ACTION);
            //intentTestService.setAction(ACTION_POSITION);
            intentTestService.putExtra(PROGRESS_POSITION,String.valueOf(i));
            //intentTestService.addFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
            sendBroadcast(intentTestService);
            Log.e("TASK", "Position");
        }

        Intent intentTestService2 = new Intent(POSITION_FIN_ACTION);
        //intentTestService2.setAction(ACTION_POSITION_FIN);
        intentTestService2.putExtra(PROGRESS_POSITION,1456);
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

   /* private void startLocationRetreiving(){

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);

    }*/
}
