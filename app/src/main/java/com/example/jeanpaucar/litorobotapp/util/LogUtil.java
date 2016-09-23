package com.example.jeanpaucar.litorobotapp.util;

import android.util.Log;

/**
 * Created by Jeancarlos Paucar on 21/07/2016.
 */
public class LogUtil {

    public static void SaveLogError(final String Tag, final String Message) {

        if(Tag != null && Message != null){
            Log.e(Tag, Message);
        }
    }

    public static void SaveLogDep(final String Tag, final String Message) {

        if(Tag != null && Message != null){
            Log.d(Tag, Message);
        }
    }

}
