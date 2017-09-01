package com.reebrandogmail.trackmycar.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.reebrandogmail.trackmycar.R;
import com.reebrandogmail.trackmycar.Util.GPSTracker;


import butterknife.ButterKnife;

public class MapsActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private BroadcastReceiver mReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_maps, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ButterKnife.bind(this, view);


        return view;
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near London, United Kingdom.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(getActivity().getIntent() != null) {
            Intent i = getActivity().getIntent();
            String remetente = i.getStringExtra("tracker");
            String mensagem = i.getStringExtra("message");
            Toast.makeText(getActivity(), remetente == null && mensagem ==null ? "" : remetente + " : " + mensagem, Toast.LENGTH_SHORT).show();
            if (mensagem != null){
                // Add a marker in Sydney and move the camera
                GPSTracker gpsTracker = getInfo(mensagem);
                LatLng yourLocation = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
                mMap.addMarker(new MarkerOptions().position(yourLocation).title(getString(R.string.your_location)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(yourLocation));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter(
                "android.intent.action.SMSRECEBIDO");

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                //extract our message from intent
                String remetente = intent.getStringExtra("tracker");
                String mensagem = intent.getStringExtra("message");
                Toast.makeText(MapsActivity.this.getContext(), remetente == null && mensagem == null ? "" : remetente + " : " + mensagem, Toast.LENGTH_SHORT).show();
                if (mensagem != null){
                    // Add a marker in Sydney and move the camera
                    GPSTracker gpsTracker = getInfo(mensagem);
                    LatLng yourLocation = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(yourLocation).title(getString(R.string.your_location)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(yourLocation));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                }

            }
        };
        //registering our receiver
        getActivity().registerReceiver(mReceiver, intentFilter);
    }

    private GPSTracker getInfo(String message){
        GPSTracker gpsTracker = new GPSTracker();
        String[] rawMessage = message.split("\n");
        //getting data from gps and storing in a GPS info class
        //lat:-23.685345
        gpsTracker.setLatitude(Float.parseFloat(rawMessage[0].split(":")[1]));
        //long:-46.528315
        gpsTracker.setLongitude(Float.parseFloat(rawMessage[1].split(":")[1]));
        //speed:0.00
        gpsTracker.setSpeed(Double.parseDouble(rawMessage[2].split(":")[1]));
        //T:17/07/16 07:51
        gpsTracker.setTimestamp(rawMessage[3].split(":",2)[1]);
        //http://maps.google.com/maps?f=q&q=-23.685345,-46.528315&z=16
        gpsTracker.setMapsURL(rawMessage[4]);
        //separates the spaces and then attributes
        //Pwr: OFF Door: OFF ACC: OFF
        gpsTracker.setDoor(rawMessage[5].split(String.valueOf(' '))[1].equals("ON")?true:false);
        gpsTracker.setPower(rawMessage[5].split(String.valueOf(' '))[3].equals("ON")?true:false);
        gpsTracker.setAcc(rawMessage[5].split(String.valueOf(' '))[5].equals("ON")?true:false);
        return gpsTracker;

    }

    @Override
    public void onPause() {
        super.onPause();
        //unregister our receiver
        getActivity().unregisterReceiver(this.mReceiver);
    }
}
