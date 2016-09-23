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
    private ImageView frag_dashboard_command_0;
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

    private Button frag_dashboard_option_1;
    private Button frag_dashboard_option_2;
    private Button frag_dashboard_option_3;
    private Button frag_dashboard_option_4;

    private InterfaceDashBoard interfaceDashBoard;

    private String data = "";

    private String COMMAND_0 = Constants.COMMAND_RIGHT;
    private String COMMAND_1 = Constants.COMMAND_RIGHT;
    private String COMMAND_2 = Constants.COMMAND_RIGHT;
    private String COMMAND_3 = Constants.COMMAND_RIGHT;
    private String COMMAND_4 = Constants.COMMAND_RIGHT;
    private String COMMAND_5 = Constants.COMMAND_RIGHT;
    private String COMMAND_6 = Constants.COMMAND_RIGHT;
    private String COMMAND_7 = Constants.COMMAND_RIGHT;
    private String COMMAND_8 = Constants.COMMAND_RIGHT;
    private String COMMAND_9 = Constants.COMMAND_RIGHT;
    private String COMMAND_10 = Constants.COMMAND_RIGHT;
    private String COMMAND_11 = Constants.COMMAND_RIGHT;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        frag_dashboard_command_0 = (ImageView) view.findViewById(R.id.frag_dashboard_command_0);
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


        frag_dashboard_option_1 = (Button) view.findViewById(R.id.frag_dashboard_option_1);
        frag_dashboard_option_2 = (Button) view.findViewById(R.id.frag_dashboard_option_2);
        frag_dashboard_option_3 = (Button) view.findViewById(R.id.frag_dashboard_option_3);
        frag_dashboard_option_4 = (Button) view.findViewById(R.id.frag_dashboard_option_4);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        frag_dashboard_command_0.setOnClickListener(this);
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

        frag_dashboard_option_1.setOnClickListener(this);
        frag_dashboard_option_2.setOnClickListener(this);
        frag_dashboard_option_3.setOnClickListener(this);
        frag_dashboard_option_4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frag_dashboard_option_1:
                interfaceDashBoard.GetOption(0, CarData());
                break;

            case R.id.frag_dashboard_option_2:
                interfaceDashBoard.GetOption(1, CarData());
                break;

            case R.id.frag_dashboard_option_3:
                interfaceDashBoard.GetOption(2, CarData());
                break;

            case R.id.frag_dashboard_option_4:
                interfaceDashBoard.GetOption(3, CarData());
                break;

            case R.id.frag_dashboard_command_0:
                COMMAND_0 = ChangeStateImage(0, v);
                break;

            case R.id.frag_dashboard_command_1:
                COMMAND_1 = ChangeStateImage(1, v);
                break;

            case R.id.frag_dashboard_command_2:
                COMMAND_2 = ChangeStateImage(2, v);
                break;

            case R.id.frag_dashboard_command_3:
                COMMAND_3 = ChangeStateImage(3, v);
                break;

            case R.id.frag_dashboard_command_4:
                COMMAND_4 = ChangeStateImage(4, v);
                break;

            case R.id.frag_dashboard_command_5:
                COMMAND_5 = ChangeStateImage(5, v);
                break;

            case R.id.frag_dashboard_command_6:
                COMMAND_6 = ChangeStateImage(6, v);
                break;

            case R.id.frag_dashboard_command_7:
                COMMAND_7 = ChangeStateImage(7, v);
                break;

            case R.id.frag_dashboard_command_8:
                COMMAND_8 = ChangeStateImage(8, v);
                break;

            case R.id.frag_dashboard_command_9:
                COMMAND_9 = ChangeStateImage(9, v);
                break;

            case R.id.frag_dashboard_command_10:
                COMMAND_10 = ChangeStateImage(10, v);
                break;

            case R.id.frag_dashboard_command_11:
                COMMAND_11 = ChangeStateImage(11, v);
                break;
        }
    }

    private String ChangeStateImage(int pos, View view) {

        String commandTemp = Constants.COMMAND_UP;

        switch (view.getTag().toString()) {

            case Constants.TAG_IMAGE_0:
                view.setTag(Constants.TAG_IMAGE_1);
                ((ImageView) view).setImageResource(R.mipmap.ic_down);
                commandTemp = Constants.COMMAND_DOWN;
                break;

            case Constants.TAG_IMAGE_1:
                view.setTag(Constants.TAG_IMAGE_2);
                ((ImageView) view).setImageResource(R.mipmap.ic_left);
                commandTemp = Constants.COMMAND_LEFT;
                break;

            case Constants.TAG_IMAGE_2:
                view.setTag(Constants.TAG_IMAGE_3);
                ((ImageView) view).setImageResource(R.mipmap.ic_up);
                commandTemp = Constants.COMMAND_UP;
                break;

            case Constants.TAG_IMAGE_3:
                view.setTag(Constants.TAG_IMAGE_0);
                ((ImageView) view).setImageResource(R.mipmap.ic_right);
                commandTemp = Constants.COMMAND_RIGHT;
                break;
        }
        return commandTemp;
    }

    private String CarData() {

        String dataTemp = "";

        dataTemp = COMMAND_0 + Constants.COMMAND_DIVIDER +
                COMMAND_1 + Constants.COMMAND_DIVIDER +
                COMMAND_2 + Constants.COMMAND_DIVIDER +
                COMMAND_3 + Constants.COMMAND_DIVIDER +
                COMMAND_4 + Constants.COMMAND_DIVIDER +
                COMMAND_5 + Constants.COMMAND_DIVIDER +
                COMMAND_6 + Constants.COMMAND_DIVIDER +
                COMMAND_7 + Constants.COMMAND_DIVIDER +
                COMMAND_8 + Constants.COMMAND_DIVIDER +
                COMMAND_9 + Constants.COMMAND_DIVIDER +
                COMMAND_10 + Constants.COMMAND_DIVIDER +
                COMMAND_11;

        return dataTemp;
    }

    public interface InterfaceDashBoard {
        void GetOption(int i, String data);
    }

    public void setInterfaceDashBoard(InterfaceDashBoard interfaceDashBoard) {
        this.interfaceDashBoard = interfaceDashBoard;
    }
}
