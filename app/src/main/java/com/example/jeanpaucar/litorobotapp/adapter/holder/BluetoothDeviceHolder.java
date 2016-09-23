package com.example.jeanpaucar.litorobotapp.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jeanpaucar.litorobotapp.R;


/**
 * Created by Jeancarlos Paucar on 03/07/2016.
 */
public class BluetoothDeviceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView item_bluetooth_device_title;
    public TextView item_bluetooth_device_info;
    private OnItemClickListener onItemClickListener;

    public BluetoothDeviceHolder(View itemView) {
        super(itemView);

        item_bluetooth_device_title = (TextView) itemView.findViewById(R.id.item_bluetooth_device_title);
        item_bluetooth_device_info = (TextView) itemView.findViewById(R.id.item_bluetooth_device_info);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(v);
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
