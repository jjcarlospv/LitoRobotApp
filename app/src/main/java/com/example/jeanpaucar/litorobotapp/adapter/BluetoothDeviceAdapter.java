package com.example.jeanpaucar.litorobotapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeanpaucar.litorobotapp.R;
import com.example.jeanpaucar.litorobotapp.adapter.holder.BluetoothDeviceHolder;
import com.example.jeanpaucar.litorobotapp.bean.BEBluetoothDevice;

import java.util.List;

/**
 * Created by Jeancarlos Paucar on 03/07/2016.
 */
public class BluetoothDeviceAdapter extends RecyclerView.Adapter<BluetoothDeviceHolder> {

    private List<BEBluetoothDevice> beBluetoothDevices;

    public BluetoothDeviceAdapter(List<BEBluetoothDevice> beBluetoothDevices) {
        this.beBluetoothDevices = beBluetoothDevices;
    }

    @Override
    public BluetoothDeviceHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth_device, parent, false);
        return new BluetoothDeviceHolder(view);
    }

    @Override
    public void onBindViewHolder(BluetoothDeviceHolder holder, final int position) {

        BEBluetoothDevice beBluetoothDevice = beBluetoothDevices.get(position);

        holder.item_bluetooth_device_title.setText(beBluetoothDevice.getTitle());
        holder.item_bluetooth_device_info.setText(beBluetoothDevice.getInfo());

        holder.setOnItemClickListener(new BluetoothDeviceHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                onItemClickListener.onItemClick(view, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return beBluetoothDevices.size();
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
