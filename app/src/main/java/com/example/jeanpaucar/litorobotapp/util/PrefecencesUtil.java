package com.example.jeanpaucar.litorobotapp.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by JEANCARLOS on 14/08/2015.
 */
public class PrefecencesUtil {
    public final static String SHARE_PREFERENCES_NAME = "com.example.jeanpaucar.litorobotapp.BLUETOOTH_CODE";
    public final static String SHARE_PREFERENCES_KEY_BLUETOOTH = "BLUETOOTH_CODE";

    private Context context;
    private SharedPreferences sharedPreferences;

    public PrefecencesUtil(Context context) {
        this.context = context;
    }

    public void setBluetoothCode(String bluetoothCode){
        context.getSharedPreferences(SHARE_PREFERENCES_NAME,Context.MODE_PRIVATE).edit().putString(SHARE_PREFERENCES_KEY_BLUETOOTH,bluetoothCode).commit();
    }

    public String getBluetoothCode(){
        return context.getSharedPreferences(SHARE_PREFERENCES_NAME,Context.MODE_PRIVATE).getString(SHARE_PREFERENCES_KEY_BLUETOOTH,null);
    }
}
