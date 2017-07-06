package com.reebrandogmail.trackmycar.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.reebrandogmail.trackmycar.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by renan.brando on 06/07/2017.
 */

public class ProfileFragment extends Fragment {

    @BindView(R.id.etName)EditText etName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_profile_fragment, container, false);
        ButterKnife.bind(this, view);

        etName.setText("Hello World");

        return view;
    }

    @OnClick(R.id.btnUpdate)
    public void updateProfile(View v){
        Toast.makeText(v.getContext(), etName.getText().toString(), Toast.LENGTH_LONG).show();
    }
}
