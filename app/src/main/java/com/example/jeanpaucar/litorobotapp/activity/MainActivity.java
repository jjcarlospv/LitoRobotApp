package com.example.jeanpaucar.litorobotapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jeanpaucar.litorobotapp.R;
import com.example.jeanpaucar.litorobotapp.adapter.PiecesAdapter;
import com.example.jeanpaucar.litorobotapp.listener.MultiTouchListener;
import com.example.jeanpaucar.litorobotapp.model.PiecesItem;
import com.example.jeanpaucar.litorobotapp.service.TaskIntentService;
import com.example.jeanpaucar.litorobotapp.service.TaskService;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ListView pieces;
    private ArrayList<PiecesItem> piecesItems;
    private Button btnTest, btnTest2;
    private TextView txtPosition;

    //private MyPositionBroadcastReceiver myPositionBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pieces = (ListView)findViewById(R.id.listViewPieces);
        piecesItems = new ArrayList<>();
        pieces.setAdapter(new PiecesAdapter(MainActivity.this,piecesItems));
        piecesItems.add(new PiecesItem("Up", R.mipmap.ic_up));
        piecesItems.add(new PiecesItem("Down",R.mipmap.ic_down));
        piecesItems.add(new PiecesItem("Left",R.mipmap.ic_left));
        piecesItems.add(new PiecesItem("Right",R.mipmap.ic_right));

        txtPosition = (TextView)findViewById(R.id.txtPosition);

        btnTest =(Button)findViewById(R.id.btnTest);
        btnTest2 =(Button)findViewById(R.id.btnTest2);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getApplication(), TaskService.class));
                Log.e("TASK_SERVICE", "Init");
            }
        });


        btnTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getApplication(), TaskIntentService.class));
                Log.e("TASK_INTENT_SERVICE", "Init");

                /*Intent intentTestService = new Intent(TaskIntentService.POSITION_ACTION);
                //intentTestService.setAction(ACTION_POSITION);
                intentTestService.putExtra(TaskIntentService.PROGRESS_POSITION,10);
                //intentTestService.addFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
                sendBroadcast(intentTestService);
                Log.e("TASK", "Position");*/
            }
        });


        /*IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TaskIntentService.ACTION_POSITION);
        intentFilter.addAction(TaskIntentService.ACTION_POSITION_FIN);
        registerReceiver(myPositionBroadcastReceiver,intentFilter);*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*public class MyPositionBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
                switch(intent.getAction()){
                    case TaskIntentService.ACTION_POSITION:
                        txtPosition.setText(intent.getIntExtra(TaskIntentService.PROGRESS_POSITION,0));
                        Log.e("BROADCAST RECEIVER", "Init");
                        break;
                }
        }
    }*/
}
