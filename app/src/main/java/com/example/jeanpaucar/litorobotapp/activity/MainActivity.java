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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jeanpaucar.litorobotapp.R;
import com.example.jeanpaucar.litorobotapp.adapter.PiecesAdapter;
import com.example.jeanpaucar.litorobotapp.model.PiecesItem;

import java.util.ArrayList;

//<a href='https://www.freepik.es/foto-gratis/ninos-en-sala-de-juegos-en-el-piso_1267763.htm'>Designed by Freepik</a>
public class MainActivity extends ActionBarActivity {

    private ListView pieces;
    private ArrayList<PiecesItem> piecesItems;
    private Button btnTest, btnTest2;
    private TextView txtPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

       /* pieces = (ListView)findViewById(R.id.listViewPieces);
        piecesItems = new ArrayList<>();
        pieces.setAdapter(new PiecesAdapter(MainActivity.this,piecesItems));
        piecesItems.add(new PiecesItem("Up", R.mipmap.ic_up));
        piecesItems.add(new PiecesItem("Down",R.mipmap.ic_down));
        piecesItems.add(new PiecesItem("Left",R.mipmap.ic_left));
        piecesItems.add(new PiecesItem("Right",R.mipmap.ic_right));

        txtPosition = (TextView)findViewById(R.id.txtPosition);

        btnTest =(Button)findViewById(R.id.btnTest);
        btnTest2 =(Button)findViewById(R.id.btnTest2);*/

        btnTest.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float currX, currY;
                float prevX, prevY;

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("ACTION_DOWN", "push button");
                        prevX = motionEvent.getX();
                        prevY = motionEvent.getY();

                        currX = motionEvent.getRawX();
                        currY = motionEvent.getRawY();

                        Log.e("prevX/ Curr", String.valueOf(prevX)+"/"+String.valueOf(currX));
                        Log.e("prevY/ Curr", String.valueOf(prevY)+"/"+String.valueOf(currY));

                        break;
                    case MotionEvent.ACTION_MOVE:
                        /*currX = motionEvent.getRawX();
                        currY = motionEvent.getRawY();

                        ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
                        marginParams.setMargins((int)(currX - prevX-420), (int)(currY - prevY-450),0, 0);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(marginParams);
                        view.setLayoutParams(layoutParams);
                        Log.e("prevX - currX",String.valueOf(prevX)+"-"+String.valueOf(currX));
                        Log.e("prevY - currY",String.valueOf(prevY)+"-"+String.valueOf(currY));*/
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        break;

                    case MotionEvent.ACTION_UP:
                        Log.d("ACTION_UP", "Release button");
                        break;
                }
                return false;
            }
        });

        btnTest2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float currX, currY;
                float prevX, prevY;

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("ACTION_DOWN", "push button");
                        prevX = motionEvent.getX();
                        prevY = motionEvent.getY();

                        currX = motionEvent.getRawX();
                        currY = motionEvent.getRawY();

                        Log.e("prevX/ Curr", String.valueOf(prevX)+"/"+String.valueOf(currX));
                        Log.e("prevY/ Curr", String.valueOf(prevY)+"/"+String.valueOf(currY));

                        break;
                    case MotionEvent.ACTION_MOVE:

                        Log.d("ACTION_DOWN", "push button");
                        prevX = motionEvent.getX();
                        prevY = motionEvent.getY();

                        currX = motionEvent.getRawX();
                        currY = motionEvent.getRawY();

                        Log.e("prevX/ Curr", String.valueOf(prevX)+"/"+String.valueOf(currX));
                        Log.e("prevY/ Curr", String.valueOf(prevY)+"/"+String.valueOf(currY));


                        /*currX = motionEvent.getRawX();
                        currY = motionEvent.getRawY();

                        ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
                        marginParams.setMargins((int)(currX - prevX-420), (int)(currY - prevY-450),0, 0);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(marginParams);
                        view.setLayoutParams(layoutParams);
                        Log.e("prevX - currX",String.valueOf(prevX)+"-"+String.valueOf(currX));
                        Log.e("prevY - currY",String.valueOf(prevY)+"-"+String.valueOf(currY));*/
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        break;

                    case MotionEvent.ACTION_UP:
                        Log.d("ACTION_UP", "Release button");
                        break;
                }
                return false;
            }
        });

       /* btnTest.setOnClickListener(new View.OnClickListener() {
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

            }
        });*/
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

}
