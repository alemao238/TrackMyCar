package com.reebrandogmail.trackmycar.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reebrandogmail.trackmycar.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by renan.brando on 09/08/2017.
 */

public class EditUserFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_profile_fragment, container, false);
        ButterKnife.bind(this, view);



        return view;
    }
}
