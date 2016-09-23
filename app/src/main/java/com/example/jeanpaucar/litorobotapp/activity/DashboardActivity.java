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
import android.util.Log;
import android.view.MenuItem;

import com.example.jeanpaucar.litorobotapp.R;
import com.example.jeanpaucar.litorobotapp.bean.BEBluetoothDevice;
import com.example.jeanpaucar.litorobotapp.fragment.BluetoothDeviceFragment;
import com.example.jeanpaucar.litorobotapp.fragment.DashboardFragment;
import com.example.jeanpaucar.litorobotapp.service.BluetoothService;
import com.example.jeanpaucar.litorobotapp.util.LogUtil;

/**
 * Created by Jeancarlos Paucar on 20/09/2016.
 */
public class DashboardActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    final String TAG_MAIN = "DashboardActivity";
    private DrawerLayout act_main_nav_drawdraw;
    private NavigationView act_main_nav_view;

    private BluetoothAdapter bluetoothAdapter;

    private DashboardFragment dashboardFragment;
    private BluetoothDeviceFragment bluetoothDeviceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        NavDrawer();
        InitFragment();
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
     * Method for Initializing Fragment
     */
    private void InitFragment() {
        dashboardFragment = new DashboardFragment();
        getFragmentManager().beginTransaction().replace(R.id.act_main_fragment_container, dashboardFragment, null).commit();
        dashboardFragment.setInterfaceDashBoard(new DashboardFragment.InterfaceDashBoard() {
            @Override
            public void GetOption(int i, String data) {
            Log.e(TAG_MAIN, data);
                switch (i) {

                    case 0:
                        break;

                    case 1:
                        break;

                    case 2:
                        break;

                    case 3:
                        break;
                }
            }
        });
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
                        dashboardFragment = new DashboardFragment();
                        getFragmentManager().beginTransaction().replace(R.id.act_main_fragment_container, dashboardFragment, null).commit();
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
            Log.e(TAG_MAIN, "MessageError2MainMenu - " + "Showing message");

        } catch (Exception e) {

            Log.e(TAG_MAIN, "MessageError2MainMenu - " + "MessageError");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_options_settings:
                BluetoothDeviceOption();
                break;
        }
        return false;
    }
}
