package com.reebrandogmail.trackmycar.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reebrandogmail.trackmycar.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by renan.brando on 10/07/2017.
 */

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_about_fragment, container, false);
        ButterKnife.bind(this, view);
        requestCallPermission();
        return view;
    }

    @OnClick(R.id.contactLink)
    public void callUs(View view){
        String phone = "983114452";
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    private void requestCallPermission() {
        String permission = Manifest.permission.CALL_PHONE;
        int grant = ContextCompat.checkSelfPermission(getActivity(), permission);
        if ( grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(getActivity(), permission_list, 1);
        }
    }
}
