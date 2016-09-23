package com.example.jeanpaucar.litorobotapp.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.jeanpaucar.litorobotapp.BluetoothConexion;
import com.example.jeanpaucar.litorobotapp.common.Constants;
import com.example.jeanpaucar.litorobotapp.util.LogUtil;


/**
 * Created by Jeancarlos Paucar on 29/06/2016.
 */
public class BluetoothService extends Service {

    private final String TAG_BLUETOOTH = "BluetoothService";
    public static BluetoothService bluetoothServiceSingleton;
    private BluetoothAdapter AdaptadorBT = null;
    private BluetoothConexion bluetoothConexion;
    private boolean isConnected = false;
    private final long DELAY_BLUETOOTH_TIMER = 5000;
    public static boolean requestSent = false;
    private Handler handler;
    private String deviceName = "";
    private boolean isServiceActive = false;
    private BroadcastBluetoothDevice broadcastBluetoothDevice;

    @Override
    public void onCreate() {
        super.onCreate();
        bluetoothServiceSingleton = this;
        isServiceActive = true;

        broadcastBluetoothDevice = new BroadcastBluetoothDevice();
        IntentFilter intentFilterBluetoothDevice = new IntentFilter(Constants.BLUETOOTH_DEVICE_ACTION_FOR_BLUETOOTH_SERVICE);
        registerReceiver(broadcastBluetoothDevice, intentFilterBluetoothDevice);

        BluetoothInit("");
        LogUtil.SaveLogDep(TAG_BLUETOOTH, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.SaveLogDep(TAG_BLUETOOTH, "onStartCommand");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        isServiceActive = false;

        getSharedPreferences(Constants.BLUETOOTH_DEVICE_SHARE, MODE_PRIVATE).edit()
                .putBoolean(Constants.BLUETOOTH_DEVICE_SHARE_STATUS, false).commit();

        if (bluetoothConexion != null) {
            bluetoothConexion.stop();
            bluetoothConexion = null;
        }

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }

        unregisterReceiver(broadcastBluetoothDevice);
        LogUtil.SaveLogError(TAG_BLUETOOTH, "onDestroy");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Method for initializing the Bluetooth connection
     *
     * @param deviceId
     */
    public void BluetoothInit(final String deviceId) {

        if (bluetoothConexion != null) {
            bluetoothConexion = null;
        }

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }

        // Making the Handler for receiving data from Bluetooth Device
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                String messageTemp = "";
                String messageForSystem = "";
                Intent intentBluetoothDevice = new Intent(Constants.BLUETOOTH_DEVICE_ACTION_FOR_BLUETOOTH_SERVICE);

                switch (msg.what) {

                    case BluetoothConexion.STATE_NONE:
                        //messageForSystem = Constants.SYSTEM_ACTION_FOR_SYSTEM;
                        //messageTemp = BluetoothConexion.MESSAGE_NONE;

                        getSharedPreferences(Constants.BLUETOOTH_DEVICE_SHARE, MODE_PRIVATE).edit()
                                .putBoolean(Constants.BLUETOOTH_DEVICE_SHARE_STATUS, false).commit();
                        break;

                    case BluetoothConexion.STATE_LISTEN:
                        //messageForSystem = Constants.SYSTEM_ACTION_FOR_SYSTEM;
                        //messageTemp = BluetoothConexion.MESSAGE_LISTEN;

                        getSharedPreferences(Constants.BLUETOOTH_DEVICE_SHARE, MODE_PRIVATE).edit()
                                .putBoolean(Constants.BLUETOOTH_DEVICE_SHARE_STATUS, false).commit();
                        break;

                    case BluetoothConexion.STATE_CONNECTING:
                        //messageForSystem = Constants.SYSTEM_ACTION_FOR_SYSTEM;
                        //messageTemp = getApplicationContext().getResources()
                        //        .getString(R.string.fragment_bluetooth_device_status_connecting);
                        //messageTemp = messageTemp + " " + msg.getData().getString(BluetoothConexion.DEVICE_NAME);

                        getSharedPreferences(Constants.BLUETOOTH_DEVICE_SHARE, MODE_PRIVATE).edit()
                                .putBoolean(Constants.BLUETOOTH_DEVICE_SHARE_STATUS, false).commit();
                        break;

                    case BluetoothConexion.STATE_CONNECTED:
                        messageForSystem = Constants.SYSTEM_ACTION_FOR_SYSTEM;
                        messageTemp = BluetoothConexion.MESSAGE_CONNECTED;
                        messageTemp = messageTemp + " to " + msg.getData().getString(BluetoothConexion.DEVICE_NAME);

                        getSharedPreferences(Constants.BLUETOOTH_DEVICE_SHARE, MODE_PRIVATE).edit()
                                .putBoolean(Constants.BLUETOOTH_DEVICE_SHARE_STATUS, true).commit();
                        break;

                    case BluetoothConexion.STATE_DISCONNECTED:
                        messageForSystem = Constants.SYSTEM_ACTION_FOR_BLUETOOTH_DEVICE;
                        messageTemp = BluetoothConexion.MESSAGE_DISCONNECTED;

                        getSharedPreferences(Constants.BLUETOOTH_DEVICE_SHARE, MODE_PRIVATE).edit()
                                .putBoolean(Constants.BLUETOOTH_DEVICE_SHARE_STATUS, false).commit();
                        break;

                    case BluetoothConexion.STATE_ERROR:
                        messageForSystem = Constants.SYSTEM_ACTION_FOR_BLUETOOTH_DEVICE;
                        messageTemp = BluetoothConexion.MESSAGE_ERROR;

                        getSharedPreferences(Constants.BLUETOOTH_DEVICE_SHARE, MODE_PRIVATE).edit()
                                .putBoolean(Constants.BLUETOOTH_DEVICE_SHARE_STATUS, false).commit();
                        break;

                    case BluetoothConexion.Mensaje_Estado_Cambiado:
                        break;

                    case BluetoothConexion.Mensaje_Leido:
                        messageForSystem = Constants.SYSTEM_ACTION_FOR_DEVICES;
                        messageTemp = msg.getData().getString(BluetoothConexion.RESPONSE_FROM_CAR);
                        break;

                    case BluetoothConexion.Mensaje_Escrito:
                        break;

                    case BluetoothConexion.Mensaje_Nombre_Dispositivo:
                        break;

                    case BluetoothConexion.Mensaje_Toast:
                        break;
                }

                switch (messageForSystem) {

                    case Constants.SYSTEM_ACTION_FOR_SYSTEM:

                        if (messageTemp != null) {

                            if (!messageTemp.equals("")) {

                                Intent intent = new Intent(Constants.BLUETOOTH_DEVICE_ACTION_FOR_SYSTEM);
                                intent.putExtra(Constants.BLUETOOTH_DEVICE_ACTION_MESSAGE, messageTemp);
                                sendBroadcast(intent);

                                LogUtil.SaveLogDep(TAG_BLUETOOTH, "Message for System from Bluetooth:" + messageTemp);
                                Toast.makeText(getApplicationContext(), messageTemp, Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;

                    case Constants.SYSTEM_ACTION_FOR_DEVICES:

                        if (messageTemp != null) {

                            if (!messageTemp.equals("")) {

                                Intent intent = new Intent(Constants.BLUETOOTH_DEVICE_ACTION_FOR_DEVICES);
                                intent.putExtra(Constants.BLUETOOTH_DEVICE_ACTION_MESSAGE, messageTemp);
                                sendBroadcast(intent);
                                LogUtil.SaveLogDep(TAG_BLUETOOTH, "Message for Devices from Bluetooth:" + messageTemp);
                            }
                        }
                        break;

                    case Constants.SYSTEM_ACTION_FOR_BLUETOOTH_DEVICE:
                        sendBroadcast(intentBluetoothDevice);
                        LogUtil.SaveLogDep(TAG_BLUETOOTH, "Broadcast Sent");
                        break;
                }
            }
        };

        String bluetoothDeviceTemp = getSharedPreferences(Constants.BLUETOOTH_DEVICE_SHARE, MODE_PRIVATE)
                .getString(Constants.BLUETOOTH_DEVICE_SHARE_DEVICE_INFO, "");

        isConnected = getSharedPreferences(Constants.BLUETOOTH_DEVICE_SHARE, MODE_PRIVATE)
                .getBoolean(Constants.BLUETOOTH_DEVICE_SHARE_STATUS, false);

        String deviceTemp = "";
        boolean initDevice = false;

        if (!bluetoothDeviceTemp.equals("")) {

            if (!isConnected) {

                LogUtil.SaveLogError(TAG_BLUETOOTH, "Currently Bluetooth device is Disconnected");
                deviceTemp = bluetoothDeviceTemp;
                initDevice = true;
            } else {
                LogUtil.SaveLogDep(TAG_BLUETOOTH, "Currently Bluetooth device is Connected");
            }

        } else {

            LogUtil.SaveLogError(TAG_BLUETOOTH, "There isn't IdDevice register");

            if (!deviceId.equals("")) {

                if (!isConnected) {
                    LogUtil.SaveLogDep(TAG_BLUETOOTH, "Currently Bluetooth device is DisConnected ... Connecting");
                    deviceTemp = deviceId;
                    initDevice = true;
                } else {
                    LogUtil.SaveLogDep(TAG_BLUETOOTH, "Currently Bluetooth device is Connected *");
                }
            } else {
                LogUtil.SaveLogError(TAG_BLUETOOTH, "There isn't IdDevice to try to connect again");
            }
        }

        if (initDevice) {

            getSharedPreferences(Constants.BLUETOOTH_DEVICE_SHARE, MODE_PRIVATE).edit()
                    .putString(Constants.BLUETOOTH_DEVICE_SHARE_DEVICE_INFO, deviceTemp).commit();

            // Obtenemos el adaptador de bluetooth
            if (AdaptadorBT == null) {
                AdaptadorBT = BluetoothAdapter.getDefaultAdapter();
            }

            if (AdaptadorBT != null) {

                if (AdaptadorBT.isEnabled()) {

                    LogUtil.SaveLogDep(TAG_BLUETOOTH, "BluetoothInit: AdaptadorBT.isEnabled()");
                    BluetoothDevice device = AdaptadorBT.getRemoteDevice(deviceTemp);
                    bluetoothConexion = new BluetoothConexion(getApplicationContext(), handler);
                    bluetoothConexion.connect(device);

                } else {

                    if (!requestSent) {

                        LogUtil.SaveLogError(TAG_BLUETOOTH, "BluetoothInit: AdaptadorBT.Disable");
                        Intent intent = new Intent(Constants.BLUETOOTH_DEVICE_ACTION_FOR_SYSTEM);
                        intent.putExtra(Constants.BLUETOOTH_DEVICE_ACTION_MESSAGE, BluetoothConexion.MESSAGE_TURN_ON_BLUETOOTH);
                        sendBroadcast(intent);
                        requestSent = true;
                    }
                }

            } else { // Device doesn't support bluetooth connection
                if (!requestSent) {
                    Intent intent = new Intent(Constants.BLUETOOTH_DEVICE_ACTION_FOR_SYSTEM);
                    intent.putExtra(Constants.BLUETOOTH_DEVICE_ACTION_MESSAGE, BluetoothConexion.MESSAGE_TURN_ON_BLUETOOTH);
                    sendBroadcast(intent);
                    requestSent = true;
                }
            }
        }
    }

    /**
     * Method to instance the service and access to it
     *
     * @return
     */
    public static BluetoothService getInstance() {
        return bluetoothServiceSingleton;
    }

    /**
     * Method for sending message to Bluetooth device
     *
     * @param message
     */
    public void SendMessage(String message) {

        try {
            if (bluetoothConexion != null) {

                int statusTemp = bluetoothConexion.getState();
                LogUtil.SaveLogDep(TAG_BLUETOOTH, "SendMessage STATUS:" + String.valueOf(statusTemp));

                if (statusTemp == BluetoothConexion.STATE_CONNECTED) {
                    if (message.length() > 0) {
                        byte[] send = message.getBytes();
                        bluetoothConexion.write(send);
                    }
                }
            }

        } catch (Exception e) {

            if (message != null) {
                LogUtil.SaveLogError(TAG_BLUETOOTH, "Error to send Command:" + message);
            } else {
                LogUtil.SaveLogError(TAG_BLUETOOTH, "Error to send Command:" + "NULL");
            }
        }
    }

    public void StopConnection() {

        try {

            LogUtil.SaveLogDep(TAG_BLUETOOTH, "StopConnection");

            if (bluetoothConexion != null) {

                int statusTemp = bluetoothConexion.getState();
                LogUtil.SaveLogDep(TAG_BLUETOOTH, "StopConnection STATUS:" + String.valueOf(statusTemp));

                if (statusTemp == BluetoothConexion.STATE_CONNECTED) {

                    getSharedPreferences(Constants.BLUETOOTH_DEVICE_SHARE, MODE_PRIVATE).edit()
                            .putString(Constants.BLUETOOTH_DEVICE_SHARE_DEVICE_INFO, "").commit();

                    bluetoothConexion.stop();
                }
            }

        } catch (Exception e) {

        }
    }

    public class BroadcastBluetoothDevice extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            switch (intent.getAction()) {
                case Constants.BLUETOOTH_DEVICE_ACTION_FOR_BLUETOOTH_SERVICE:
                    LogUtil.SaveLogDep(TAG_BLUETOOTH, "Broadcast Received");
                    BluetoothInit("");
                    break;
            }
        }
    }
}
