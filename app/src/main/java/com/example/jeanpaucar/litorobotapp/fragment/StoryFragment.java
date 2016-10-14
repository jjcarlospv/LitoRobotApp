package com.example.jeanpaucar.litorobotapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeanpaucar.litorobotapp.R;

/**
 * Created by jose.paucar on 13/10/2016.
 */
public class StoryFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_story, container, false);
        return view;
    }
}
