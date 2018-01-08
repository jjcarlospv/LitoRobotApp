package com.example.jeanpaucar.litorobotapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.example.jeanpaucar.litorobotapp.R;
import com.example.jeanpaucar.litorobotapp.bean.BEBluetoothDevice;
import com.example.jeanpaucar.litorobotapp.common.Constants;
import com.example.jeanpaucar.litorobotapp.fragment.BluetoothDeviceFragment;
import com.example.jeanpaucar.litorobotapp.fragment.DashboardFragment;
import com.example.jeanpaucar.litorobotapp.fragment.StoryFragment;
import com.example.jeanpaucar.litorobotapp.service.BluetoothService;
import com.example.jeanpaucar.litorobotapp.util.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jeancarlos Paucar on 20/09/2016.
 */
public class DashboardActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener, DashboardFragment.InterfaceDashBoard {

    final String TAG_MAIN = "DashboardActivity";
    private DrawerLayout act_main_nav_drawdraw;
    private NavigationView act_main_nav_view;

    private BluetoothAdapter bluetoothAdapter;

    private DashboardFragment dashboardFragment;
    private BluetoothDeviceFragment bluetoothDeviceFragment;
    private StoryFragment storyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        NavDrawer();
        Table();
        InitBluetooth();
    }

    /**
     * Method for Initializing Navigation Drawer
     */
    private void NavDrawer() {
        act_main_nav_drawdraw = (DrawerLayout) findViewById(R.id.act_main_nav_drawdraw);
        act_main_nav_view = (NavigationView) findViewById(R.id.act_main_nav_view);
        act_main_nav_view.setNavigationItemSelectedListener(this);
    }

    /**
     * Method for Initializing Bluetooth
     */
    private void InitBluetooth() {

        Intent intentBluetooth = new Intent(DashboardActivity.this, BluetoothService.class);
        startService(intentBluetooth);
        LogUtil.SaveLogError(TAG_MAIN, "Init BluetoothService");
    }

    /**
     * Method for Initializing Devide Bluetooth
     */
    private void BluetoothDeviceOption() {

        String titleTemp = getResources().getString(R.string.fragment_bluetooth_device_message_title);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter != null) {

            if (bluetoothAdapter.isEnabled()) {

                bluetoothDeviceFragment = new BluetoothDeviceFragment();
                getFragmentManager().beginTransaction().replace(R.id.act_main_fragment_container, bluetoothDeviceFragment).commit();
                bluetoothDeviceFragment.setInterfaceBluetoohDevice(new BluetoothDeviceFragment.InterfaceBluetoohDevice() {
                    @Override
                    public void getBluetoothDevice(BEBluetoothDevice beBluetoothDevice) {

                        String bluetoothDeviceInfo = beBluetoothDevice.getInfo();
                        LogUtil.SaveLogDep(TAG_MAIN, "BluetoothDevice Chosen:" + beBluetoothDevice.getTitle() + "/" + bluetoothDeviceInfo);

                        StartBluetoothConnection(bluetoothDeviceInfo);

                    }

                    @Override
                    public void stopBluetoothDeviceConnection() {
                        StopBluetoothConnection();
                    }
                });

            } else {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(enableBluetooth);
            }
        } else {
            MessageError2MainMenu(titleTemp, getResources().getString(R.string.fragment_bluetooth_device_message_not_support));

            LogUtil.SaveLogDep(TAG_MAIN, "Device doesn't support bluetooth");

        }

    }

    private void StoryLito() {

        storyFragment = new StoryFragment();
        getFragmentManager().beginTransaction().replace(R.id.act_main_fragment_container, storyFragment).commit();
    }


    private void Table() {
        dashboardFragment = new DashboardFragment();
        getFragmentManager().beginTransaction().replace(R.id.act_main_fragment_container, dashboardFragment, null).commit();
        dashboardFragment.setInterfaceDashBoard(this);
    }

    /**
     * Method for sending Command to Car
     *
     * @param commandFormFragment
     */
    private void SetCommandOnCar(final String commandFormFragment) {

        if (commandFormFragment != null) {
            if (BluetoothService.getInstance() != null) {
                BluetoothService.getInstance().SendMessage(commandFormFragment);
            }
        }
    }

    /**
     * Method for stopping Bluetooth connection
     */
    private void StartBluetoothConnection(final String IdDevice) {

        if (BluetoothService.getInstance() != null) {
            BluetoothService.getInstance().BluetoothInit(IdDevice);
        }
    }

    /**
     * Method for stopping Bluetooth connection
     */
    private void StopBluetoothConnection() {

        if (BluetoothService.getInstance() != null) {
            BluetoothService.getInstance().StopConnection();
        }
    }

    /**
     * Metodo para mostrar un mensaje de error
     *
     * @param title
     */
    private void MessageError2MainMenu(final String title, final String message) {

        try {
            AlertDialog.Builder b = new AlertDialog.Builder(DashboardActivity.this);
            final AlertDialog create = b.create();
            create.setTitle(title);
            create.setCancelable(false);
            create.setMessage(message);
            create.setButton(getResources().getString(R.string.fragment_bluetooth_device_message_btn_ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            create.cancel();
                        }
                    });

            create.show();
            LogUtil.SaveLogError(TAG_MAIN, "MessageError2MainMenu - " + "Showing message");

        } catch (Exception e) {

            LogUtil.SaveLogError(TAG_MAIN, "MessageError2MainMenu - " + "MessageError");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_options_settings:
                BluetoothDeviceOption();
                break;

            case R.id.menu_options_lito_story:
                StoryLito();
                break;

            case R.id.menu_options_table:
                Table();
                break;
        }
        return false;
    }

    @Override
    public void GetOption(int i, String data) {

        if (commandTimer != null) {
            commandTimer.cancel();
            commandTimer = null;
            LogUtil.SaveLogError(TAG_MAIN, "Timer = NULL");
        }

        String[] tempCommand = data.split(Constants.COMMAND_DIVIDER);
        commandList = CleanData(tempCommand);

        if (commandList != null) {
            if (commandList.length > 0) {
                commandPosition = 0;
                commandTimer = new Timer();
                commandTimer.schedule(new TimerCommand(), 0, 500);
            }
        }

        switch (i) {

            case 0:
                LogUtil.SaveLogError(TAG_MAIN, "C0:" + data);
                break;

            case 1:
                LogUtil.SaveLogError(TAG_MAIN, "C1:" + data);
                break;

            case 2:
                LogUtil.SaveLogError(TAG_MAIN, "C2:" + data);
                break;

            case 3:
                LogUtil.SaveLogError(TAG_MAIN, "C3:" + data);
                break;

            case 4:
                LogUtil.SaveLogError(TAG_MAIN, "C4:" + data);
                break;
        }
    }

    private String[] CleanData(final String[] dataTemp) {

        String[] temp = null;
        ArrayList<String> list = new ArrayList<String>();

        if (dataTemp != null) {

            for (int i = 0; i < dataTemp.length; i++) {

                if (!dataTemp[i].equals(Constants.COMMAND_WITHOUT_COMMAND)) {
                    list.add(dataTemp[i]);
                }
            }
            temp = list.toArray(new String[list.size()]);
        } else {
            return null;
        }
        return temp;
    }


    private String[] commandList = null;
    private Timer commandTimer = null;
    private static int commandPosition = 0;


    private class TimerCommand extends TimerTask {

        String commTemp = "C*3#";
        String comm = "C*3#";
        int size = 0;

        @Override
        public void run() {

            if (commandList != null) {

                size = commandList.length;
                if (size > 0) {

                    if (size > commandPosition) {

                        comm = commTemp.replace("*", commandList[commandPosition]);
                        SetCommandOnCar(comm);
                        LogUtil.SaveLogDep(TAG_MAIN, comm + "(" + String.valueOf(commandPosition) + "/" + String.valueOf(size) + ")");
                        commandPosition++;
                    } else {
                        LogUtil.SaveLogDep(TAG_MAIN, "Timer canceled");
                        this.cancel();
                    }
                }
            }
        }
    }
}
