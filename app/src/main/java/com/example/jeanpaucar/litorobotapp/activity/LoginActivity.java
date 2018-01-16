package com.example.jeanpaucar.litorobotapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jeanpaucar.litorobotapp.R;

/**
 * Created by jose.paucar on 20/09/2016.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private Button act_login_btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        act_login_btn_login = findViewById(R.id.act_login_btn_login);
        act_login_btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.act_login_btn_login:

                Intent intentDashBoard = new Intent(this, DashboardActivity.class);
                startActivity(intentDashBoard);
                break;
        }
    }
}
