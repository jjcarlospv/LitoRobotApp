package com.example.jeanpaucar.litorobotapp.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.jeanpaucar.litorobotapp.R;
import com.example.jeanpaucar.litorobotapp.adapter.PiecesAdapter;
import com.example.jeanpaucar.litorobotapp.model.PiecesItem;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ListView pieces;
    private ArrayList<PiecesItem> piecesItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pieces = (ListView)findViewById(R.id.listViewPieces);
        piecesItems = new ArrayList<>();
        pieces.setAdapter(new PiecesAdapter(MainActivity.this,piecesItems));
        piecesItems.add(new PiecesItem("Front", R.mipmap.ic_launcher));
        piecesItems.add(new PiecesItem("Rear",R.mipmap.ic_launcher));
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
