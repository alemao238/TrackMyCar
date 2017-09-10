package com.reebrandogmail.trackmycar.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reebrandogmail.trackmycar.MainActivity;
import com.reebrandogmail.trackmycar.R;

import butterknife.ButterKnife;

/**
 * Created by renan.brando on 06/07/2017.
 */

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_settings_fragment, container, false);
        ButterKnife.bind(this, view);

        final FloatingActionButton fab = ((MainActivity) getActivity()).getFloatingActionButton();

        if (fab != null) {
            ((MainActivity) getActivity()).hideFloatingActionButton();
        }

        return view;
    }
}
