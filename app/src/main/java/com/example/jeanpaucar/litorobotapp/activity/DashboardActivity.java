package com.example.jeanpaucar.litorobotapp.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.jeanpaucar.litorobotapp.R;
import com.example.jeanpaucar.litorobotapp.fragment.DashboardFragment;

/**
 * Created by jose.paucar on 20/09/2016.
 */
public class DashboardActivity extends Activity{

    private DashboardFragment dashboardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        dashboardFragment = new DashboardFragment();
        getFragmentManager().beginTransaction().replace(R.id.act_main_fragment_container, dashboardFragment, null).commit();
    }
}
