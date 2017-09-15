package com.reebrandogmail.trackmycar.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reebrandogmail.trackmycar.R;
import com.reebrandogmail.trackmycar.Util.DBHandler;
import com.reebrandogmail.trackmycar.adapter.DividerItemDecoration;
import com.reebrandogmail.trackmycar.adapter.HistoryAdapter;
import com.reebrandogmail.trackmycar.adapter.VehicleAdapter;
import com.reebrandogmail.trackmycar.model.History;
import com.reebrandogmail.trackmycar.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by renan.brando on 18/07/2017.
 */

public class VehicleTab extends Fragment {

    private List<Vehicle> vehiclesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private VehicleAdapter mAdapter;
    private DBHandler db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_vehicle_tab, container, false);
        ButterKnife.bind(this, view);

        db = new DBHandler(view.getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.rvVehicle);

        mAdapter = new VehicleAdapter(getActivity(), vehiclesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        prepareVehiclesData();

        return view;
    }

    private void prepareVehiclesData() {
        List<Vehicle> vehicles = db.getAllVehicles();
        for (Vehicle vehicle : vehicles){
            vehiclesList.add(vehicle);
        }
        mAdapter.notifyDataSetChanged();
    }

    // Generic method for swapping fragments in the activity with extra value
    private void swapFragments(int activity, Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(activity, fragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

    @OnClick(R.id.fbAddVehicle)
    public void add(){
        swapFragments(R.id.fragment_main, new AddVehicleFragment());
    }
}
