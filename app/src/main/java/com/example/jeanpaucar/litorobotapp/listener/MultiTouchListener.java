package com.example.jeanpaucar.litorobotapp.listener;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.jeanpaucar.litorobotapp.activity.MainActivity;

/**
 * Created by JEANCARLOS on 06/08/2015.
 */
public class MultiTouchListener implements View.OnTouchListener{
    private float prevX;
    private float prevY;
    public MainActivity mainActivity;

    public MultiTouchListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float currX, currY;
        switch(motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Log.d("ACTION_DOWN","push button");
                prevX = motionEvent.getX();
                prevY = motionEvent.getY();
                Log.e("prevX",String.valueOf(prevX));
                Log.e("prevY",String.valueOf(prevY));

                break;
            case MotionEvent.ACTION_MOVE:
                currX = motionEvent.getRawX();
                currY = motionEvent.getRawY();

                ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
                marginParams.setMargins((int)(currX - prevX-420), (int)(currY - prevY-450),0, 0);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(marginParams);
                view.setLayoutParams(layoutParams);
                Log.e("prevX - currX",String.valueOf(prevX)+"-"+String.valueOf(currX));
                Log.e("prevY - currY",String.valueOf(prevY)+"-"+String.valueOf(currY));
                break;

            case MotionEvent.ACTION_CANCEL:
                break;

            case MotionEvent.ACTION_UP:
                Log.d("ACTION_UP","Release button");
                break;
        }

        return false;
    }
}
