package com.example.jeanpaucar.litorobotapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;

import com.example.jeanpaucar.litorobotapp.R;
import com.example.jeanpaucar.litorobotapp.common.Constants;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jeancarlos Paucar on 26/05/2016.
 */
public class SplashActivity extends Activity {

    private Timer timerSplash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        timerSplash = new Timer();
        timerSplash.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intentMainMenu = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intentMainMenu);
                finish();
            }
        }, Constants.DELAY_TIMER);
    }
}
