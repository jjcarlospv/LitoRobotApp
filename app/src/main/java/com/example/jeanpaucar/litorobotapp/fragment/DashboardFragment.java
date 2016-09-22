package com.example.jeanpaucar.litorobotapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.jeanpaucar.litorobotapp.R;
import com.example.jeanpaucar.litorobotapp.common.Constants;

/**
 * Created by Jeancarlos Paucar on 20/09/2016.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ImageView frag_dashboard_command_1;
    private ImageView frag_dashboard_command_2;
    private ImageView frag_dashboard_command_3;
    private ImageView frag_dashboard_command_4;
    private ImageView frag_dashboard_command_5;
    private ImageView frag_dashboard_command_6;
    private ImageView frag_dashboard_command_7;
    private ImageView frag_dashboard_command_8;
    private ImageView frag_dashboard_command_9;
    private ImageView frag_dashboard_command_10;
    private ImageView frag_dashboard_command_11;
    private ImageView frag_dashboard_command_12;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        frag_dashboard_command_1 = (ImageView) view.findViewById(R.id.frag_dashboard_command_1);
        frag_dashboard_command_2 = (ImageView) view.findViewById(R.id.frag_dashboard_command_2);
        frag_dashboard_command_3 = (ImageView) view.findViewById(R.id.frag_dashboard_command_3);
        frag_dashboard_command_4 = (ImageView) view.findViewById(R.id.frag_dashboard_command_4);
        frag_dashboard_command_5 = (ImageView) view.findViewById(R.id.frag_dashboard_command_5);
        frag_dashboard_command_6 = (ImageView) view.findViewById(R.id.frag_dashboard_command_6);
        frag_dashboard_command_7 = (ImageView) view.findViewById(R.id.frag_dashboard_command_7);
        frag_dashboard_command_8 = (ImageView) view.findViewById(R.id.frag_dashboard_command_8);
        frag_dashboard_command_9 = (ImageView) view.findViewById(R.id.frag_dashboard_command_9);
        frag_dashboard_command_10 = (ImageView) view.findViewById(R.id.frag_dashboard_command_10);
        frag_dashboard_command_11 = (ImageView) view.findViewById(R.id.frag_dashboard_command_11);
        frag_dashboard_command_12 = (ImageView) view.findViewById(R.id.frag_dashboard_command_12);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        frag_dashboard_command_1.setOnClickListener(this);
        frag_dashboard_command_2.setOnClickListener(this);
        frag_dashboard_command_3.setOnClickListener(this);
        frag_dashboard_command_4.setOnClickListener(this);
        frag_dashboard_command_5.setOnClickListener(this);
        frag_dashboard_command_6.setOnClickListener(this);
        frag_dashboard_command_7.setOnClickListener(this);
        frag_dashboard_command_8.setOnClickListener(this);
        frag_dashboard_command_9.setOnClickListener(this);
        frag_dashboard_command_10.setOnClickListener(this);
        frag_dashboard_command_11.setOnClickListener(this);
        frag_dashboard_command_12.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ChangeStateImage(v);
       /* switch (v.getId()) {

            case R.id.frag_dashboard_command_1:
                ChangeStateImage(v);
                break;

            case R.id.frag_dashboard_command_2:
                ChangeStateImage(v);
                break;

            case R.id.frag_dashboard_command_3:
                ChangeStateImage(v);
                break;
            case R.id.frag_dashboard_command_4:
                ChangeStateImage(v);
                break;

            case R.id.frag_dashboard_command_5:
                ChangeStateImage(v);
                break;

            case R.id.frag_dashboard_command_6:
                ChangeStateImage(v);
                break;

            case R.id.frag_dashboard_command_7:
                ChangeStateImage(v);
                break;

            case R.id.frag_dashboard_command_8:
                ChangeStateImage(v);
                break;

            case R.id.frag_dashboard_command_9:
                ChangeStateImage(v);
                break;

            case R.id.frag_dashboard_command_10:
                ChangeStateImage(v);
                break;

            case R.id.frag_dashboard_command_11:
                ChangeStateImage(v);
                break;

            case R.id.frag_dashboard_command_12:
                ChangeStateImage(v);
                break;

        }*/
    }

    public void ChangeStateImage(View view) {

        switch (view.getTag().toString()) {

            case Constants.TAG_IMAGE_0:
                view.setTag(Constants.TAG_IMAGE_1);
                ((ImageView)view).setImageResource(R.mipmap.ic_down);
                break;

            case Constants.TAG_IMAGE_1:
                view.setTag(Constants.TAG_IMAGE_2);
                ((ImageView)view).setImageResource(R.mipmap.ic_left);
                break;

            case Constants.TAG_IMAGE_2:
                view.setTag(Constants.TAG_IMAGE_3);
                ((ImageView)view).setImageResource(R.mipmap.ic_up);
                break;

            case Constants.TAG_IMAGE_3:
                view.setTag(Constants.TAG_IMAGE_0);
                ((ImageView)view).setImageResource(R.mipmap.ic_right);
                break;
        }
    }
}
