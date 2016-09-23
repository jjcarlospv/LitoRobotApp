package com.example.jeanpaucar.litorobotapp.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.jeanpaucar.litorobotapp.R;
import com.example.jeanpaucar.litorobotapp.adapter.BluetoothDeviceAdapter;
import com.example.jeanpaucar.litorobotapp.adapter.DividerItemDecorationAdapter;
import com.example.jeanpaucar.litorobotapp.bean.BEBluetoothDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Jeancarlos Paucar on 1/07/2016.
 */
public class BluetoothDeviceFragment extends Fragment implements View.OnClickListener {

    private final String TAG_BLUETOOTH = "BluetoothDeviceFragment";

    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;

    private View view;
    private Button fragment_bluetooth_device_btn_stop;
    private Button fragment_bluetooth_device_btn_scan;
    private Button fragment_bluetooth_device_btn_set_device;
    private RecyclerView fragment_bluetooth_device_paired_devices;
    private RecyclerView fragment_bluetooth_device_new_devices;

    private RecyclerView.LayoutManager layoutManagerNewDevice;
    private RecyclerView.LayoutManager layoutManagerPairedDevice;

    private BluetoothDeviceAdapter bluetoothDeviceAdapterPairedDevice;
    private BluetoothDeviceAdapter bluetoothDeviceAdapterNewDevice;

    private List<BEBluetoothDevice> beBluetoothDevicesPairedDevices;
    private List<BEBluetoothDevice> beBluetoothDevicesNewDevices;

    private InterfaceBluetoohDevice interfaceBluetoohDevice;
    private BEBluetoothDevice beBluetoothDevice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_bluetooth_device, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragment_bluetooth_device_btn_stop = (Button)view.findViewById(R.id.fragment_bluetooth_device_btn_stop);
        fragment_bluetooth_device_btn_scan = (Button) view.findViewById(R.id.fragment_bluetooth_device_btn_scan);
        fragment_bluetooth_device_btn_set_device = (Button) view.findViewById(R.id.fragment_bluetooth_device_btn_set_device);
        fragment_bluetooth_device_paired_devices = (RecyclerView) view.findViewById(R.id.fragment_bluetooth_device_paired_devices);
        fragment_bluetooth_device_new_devices = (RecyclerView) view.findViewById(R.id.fragment_bluetooth_device_new_devices);

        fragment_bluetooth_device_btn_set_device.setEnabled(false);

        fragment_bluetooth_device_btn_stop.setOnClickListener(this);
        fragment_bluetooth_device_btn_scan.setOnClickListener(this);
        fragment_bluetooth_device_btn_set_device.setOnClickListener(this);

        //Definición de lista para los dispositivos pareados anteriormente
        layoutManagerPairedDevice = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        fragment_bluetooth_device_paired_devices.setLayoutManager(layoutManagerPairedDevice);
        fragment_bluetooth_device_paired_devices.addItemDecoration(new DividerItemDecorationAdapter(getActivity().getResources().getDrawable(R.drawable.divider)));
        fragment_bluetooth_device_paired_devices.setItemAnimator(new DefaultItemAnimator());

        beBluetoothDevicesPairedDevices = new ArrayList<BEBluetoothDevice>();
        bluetoothDeviceAdapterPairedDevice = new BluetoothDeviceAdapter(beBluetoothDevicesPairedDevices);
        fragment_bluetooth_device_paired_devices.setAdapter(bluetoothDeviceAdapterPairedDevice);

        bluetoothDeviceAdapterPairedDevice.setOnItemClickListener(new BluetoothDeviceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                beBluetoothDevice = new BEBluetoothDevice();
                beBluetoothDevice.setTitle(beBluetoothDevicesPairedDevices.get(position).getTitle());
                beBluetoothDevice.setInfo(beBluetoothDevicesPairedDevices.get(position).getInfo());
                fragment_bluetooth_device_btn_set_device.setEnabled(true);
            }
        });

        //Definición de lista para los nuevos dispositivos encontrados
        layoutManagerNewDevice = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        fragment_bluetooth_device_new_devices.setLayoutManager(layoutManagerNewDevice);
        fragment_bluetooth_device_new_devices.addItemDecoration(new DividerItemDecorationAdapter(getActivity().getResources().getDrawable(R.drawable.divider)));
        fragment_bluetooth_device_new_devices.setItemAnimator(new DefaultItemAnimator());

        beBluetoothDevicesNewDevices = new ArrayList<BEBluetoothDevice>();
        bluetoothDeviceAdapterNewDevice = new BluetoothDeviceAdapter(beBluetoothDevicesNewDevices);
        fragment_bluetooth_device_new_devices.setAdapter(bluetoothDeviceAdapterNewDevice);


        bluetoothDeviceAdapterNewDevice.setOnItemClickListener(new BluetoothDeviceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                beBluetoothDevice = new BEBluetoothDevice();
                beBluetoothDevice.setTitle(beBluetoothDevicesNewDevices.get(position).getTitle());
                beBluetoothDevice.setInfo(beBluetoothDevicesNewDevices.get(position).getInfo());
                fragment_bluetooth_device_btn_set_device.setEnabled(true);
            }
        });

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getActivity().registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        getActivity().registerReceiver(mReceiver, filter);

        ActiveBluetoothDevice();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }
        getActivity().unregisterReceiver(mReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fragment_bluetooth_device_btn_stop:
                fragment_bluetooth_device_btn_set_device.setEnabled(false);
                interfaceBluetoohDevice.stopBluetoothDeviceConnection();
                break;

            case R.id.fragment_bluetooth_device_btn_scan:
                ActiveBluetoothDevice();
                break;

            case R.id.fragment_bluetooth_device_btn_set_device:

                if (beBluetoothDevice != null) {
                    interfaceBluetoohDevice.getBluetoothDevice(beBluetoothDevice);
                }
                break;
        }
    }

    public interface InterfaceBluetoohDevice {
        void getBluetoothDevice(BEBluetoothDevice beBluetoothDevice);
        void stopBluetoothDeviceConnection();
    }

    public void setInterfaceBluetoohDevice(InterfaceBluetoohDevice interfaceBluetoohDevice) {
        this.interfaceBluetoohDevice = interfaceBluetoohDevice;
    }

    /**
     * Metodo para activar la busqueda de dispositivos bluetooth
     */
    private void ActiveBluetoothDevice() {

        String titleTemp = getActivity().getResources().getString(R.string.fragment_bluetooth_device_message_title);

        beBluetoothDevicesPairedDevices.clear();
        bluetoothDeviceAdapterPairedDevice.notifyDataSetChanged();

        beBluetoothDevicesNewDevices.clear();
        bluetoothDeviceAdapterNewDevice.notifyDataSetChanged();

        if (mBtAdapter != null) {
            mBtAdapter = null;
        }

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBtAdapter != null) {

            if (mBtAdapter.isEnabled()) {

                if (mBtAdapter.isDiscovering()) {
                    mBtAdapter.cancelDiscovery();
                }

                Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

                // If there are paired devices, add each one to the ArrayAdapter
                BEBluetoothDevice beBluetoothDevice = null;

                if (pairedDevices.size() > 0) {

                    for (BluetoothDevice device : pairedDevices) {

                        beBluetoothDevice = new BEBluetoothDevice();
                        beBluetoothDevice.setTitle(device.getName());
                        beBluetoothDevice.setInfo(device.getAddress());
                        beBluetoothDevicesPairedDevices.add(beBluetoothDevice);
                    }

                } else {
                    String noDevices = getResources().getText(R.string.fragment_bluetooth_device_no_paired_devices).toString();
                    beBluetoothDevice = new BEBluetoothDevice();
                    beBluetoothDevice.setTitle(noDevices);
                    beBluetoothDevice.setInfo("");
                    beBluetoothDevicesPairedDevices.add(beBluetoothDevice);
                }

                bluetoothDeviceAdapterPairedDevice.notifyDataSetChanged();
                mBtAdapter.startDiscovery();

            } else {
                MessageError(titleTemp, getActivity().getResources().getString(R.string.fragment_bluetooth_device_message_message));
            }
        } else {
            MessageError(titleTemp, getActivity().getResources().getString(R.string.fragment_bluetooth_device_message_not_support));
        }
    }

    /**
     * Broadcast for getting bluetooth devices
     */
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            BEBluetoothDevice beBluetoothDeviceNew = null;
            int sizeTemp = 0;
            boolean isSameDevice = false;

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                beBluetoothDeviceNew = new BEBluetoothDevice();
                beBluetoothDeviceNew.setTitle(device.getName());
                beBluetoothDeviceNew.setInfo(device.getAddress());

                if (beBluetoothDevicesNewDevices != null) {
                    sizeTemp = beBluetoothDevicesNewDevices.size();

                    if (sizeTemp > 0) {

                        for (int k = 0; k < sizeTemp; k++) {

                            if (beBluetoothDevicesNewDevices.get(k).getInfo().equals(beBluetoothDeviceNew.getInfo())) {
                                isSameDevice = true;
                                break;
                            }
                        }
                        if (!isSameDevice) {
                            beBluetoothDevicesNewDevices.add(beBluetoothDeviceNew);
                        }
                    } else {
                        beBluetoothDevicesNewDevices.add(beBluetoothDeviceNew);
                    }
                }

                // If it's already paired, skip it, because it's been listed already
                //if (device.getBondState() != BluetoothDevice.BOND_BONDED) {


                bluetoothDeviceAdapterNewDevice.notifyDataSetChanged();
            }
        }
    };

    /**
     * Metodo para mostrar un mensaje de error
     *
     * @param title
     * @param message
     */
    private void MessageError(final String title, final String message) {

        try {
            AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
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
            Log.e(TAG_BLUETOOTH, "Showing message");

        } catch (Exception e) {

            Log.e(TAG_BLUETOOTH, "MessageError");
        }
    }


}


