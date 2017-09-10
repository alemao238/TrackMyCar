package com.reebrandogmail.trackmycar.fragments;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reebrandogmail.trackmycar.MainActivity;
import com.reebrandogmail.trackmycar.R;

import butterknife.ButterKnife;

public class MainFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_main_fragment, container, false);
        ButterKnife.bind(this, view);

        final FloatingActionButton fab = ((MainActivity) getActivity()).getFloatingActionButton();

        if (fab != null) {
            ((MainActivity) getActivity()).showFloatingActionButton();
        }

        return view;
    }


}

