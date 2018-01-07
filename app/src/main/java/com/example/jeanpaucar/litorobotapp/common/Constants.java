package com.example.jeanpaucar.litorobotapp.common;

/**
 * Created by jose.paucar on 20/09/2016.
 */
public class Constants {

    public static final String PACKAGE_NAME = "com.example.jeanpaucar.litorobotapp";

    public static long DELAY_TIMER = 2000;

    public static final String TAG_IMAGE_0 = "0"; // Without command
    public static final String TAG_IMAGE_1 = "1"; // Up
    public static final String TAG_IMAGE_2 = "2"; // Right
    public static final String TAG_IMAGE_3 = "3"; // Down
    public static final String TAG_IMAGE_4 = "4"; // Left
    public static final String TAG_IMAGE_5 = "5"; // Stop

    // Action for sending message to system
    public static final String SYSTEM_ACTION_FOR_SYSTEM = "MessageForSystem";
    public static final String SYSTEM_ACTION_FOR_DEVICES = "MessageForDevices";
    public static final String SYSTEM_ACTION_FOR_BLUETOOTH_DEVICE = "MessageForBluetoothDevice";

    // BluetoothConexion
    public static final String BLUETOOTH_DEVICE_EXTRA = PACKAGE_NAME + "BluetoothDeviceExtra";
    public static final String BLUETOOTH_DEVICE_EXTRA_DEVICE_NAME = BLUETOOTH_DEVICE_EXTRA + "DeviceName";

    public static final String BLUETOOTH_DEVICE_ACTION = PACKAGE_NAME + "BluetoothDeviceAction";
    public static final String BLUETOOTH_DEVICE_ACTION_FOR_DEVICES = BLUETOOTH_DEVICE_ACTION + "ForDevices";
    public static final String BLUETOOTH_DEVICE_ACTION_FOR_BLUETOOTH_SERVICE = BLUETOOTH_DEVICE_ACTION + "ForBluetoothService";
    public static final String BLUETOOTH_DEVICE_ACTION_FOR_SYSTEM = BLUETOOTH_DEVICE_ACTION + "ForSystem";
    public static final String BLUETOOTH_DEVICE_ACTION_MESSAGE = BLUETOOTH_DEVICE_ACTION + "Message";
    public static final int BLUETOOTH_DEVICE_ACTION_REQUEST = 10;

    public static final String BLUETOOTH_DEVICE_SHARE = PACKAGE_NAME + "BluetoothDeviceShare";
    public static final String BLUETOOTH_DEVICE_SHARE_DEVICE_NAME = BLUETOOTH_DEVICE_SHARE + "Name";
    public static final String BLUETOOTH_DEVICE_SHARE_DEVICE_INFO = BLUETOOTH_DEVICE_SHARE + "Info";
    public static final String BLUETOOTH_DEVICE_SHARE_STATUS = BLUETOOTH_DEVICE_SHARE + "Status";


    // Commands for car

    public static final String COMMAND_DIVIDER = ":";
    public static final String COMMAND_END = "#";
    public static final String COMMAND_LEFT = "L";
    public static final String COMMAND_FORWARD = "F";
    public static final String COMMAND_RIGHT = "R";
    public static final String COMMAND_BACK = "B";
    public static final String COMMAND_STOP = "S";
    public static final String COMMAND_WITHOUT_COMMAND = "W";
}
