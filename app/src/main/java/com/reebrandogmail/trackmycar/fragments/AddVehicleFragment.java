package com.reebrandogmail.trackmycar.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.reebrandogmail.trackmycar.R;
import com.reebrandogmail.trackmycar.Util.DBHandler;
import com.reebrandogmail.trackmycar.Util.Mask;
import com.reebrandogmail.trackmycar.model.Vehicle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by renan.brando on 15/09/2017.
 */

public class AddVehicleFragment extends Fragment {

    @BindView(R.id.etBrandA) EditText etBrand;
    @BindView(R.id.etModelA) EditText etModel;
    @BindView(R.id.etPlateA) EditText etPlate;
    @BindView(R.id.etYearA) EditText etYear;
    @BindView(R.id.etColorA) EditText etColor;

    Vehicle vehicle;
    DBHandler db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_add_vehicle_fragment, container, false);
        ButterKnife.bind(this, view);

        etPlate.addTextChangedListener(Mask.insert("###-####", etPlate));

        db = new DBHandler(getContext());

        vehicle = new Vehicle();

        return view;
    }

    @OnClick(R.id.btnCancel)
    public void cancel(){
        goBack();
    }

    public void goBack(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_main, new ProfileFragment());
        transaction.disallowAddToBackStack();
        transaction.commit();
    }


    @OnClick(R.id.btnConfirmAddVehicle)
    public void submitVehicle(){
        if (!isValidField(etBrand)){
            etBrand.setError("Invalid input");
        }
        else if (!isValidField(etModel)){
            etModel.setError("Invalid input");
        }
        else if (!isValidField(etPlate)){
            etPlate.setError("Invalid input");
        }
        else if (!isValidField(etYear)){
            etYear.setError("Invalid input");
        }
        else if (!isValidField(etColor)){
            etColor.setError("Invalid input");
        }
        else {
            this.vehicle.setBrand(etBrand.getText().toString());
            this.vehicle.setModel(etModel.getText().toString());
            this.vehicle.setLicensePlate(etPlate.getText().toString());
            this.vehicle.setYear(etYear.getText().toString());
            this.vehicle.setColor(etColor.getText().toString());
            db.addVehicle(this.vehicle);
            goBack();
        }

    }

    private boolean isValidField(EditText editText){
        return !editText.getText().toString().isEmpty();
    }

}