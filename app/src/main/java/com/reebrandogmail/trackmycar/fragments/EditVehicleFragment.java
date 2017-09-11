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
 * Created by renan.brando on 09/08/2017.
 */

public class EditVehicleFragment extends Fragment {

    @BindView(R.id.etBrand)  EditText etBrand;
    @BindView(R.id.etModel) EditText etModel;
    @BindView(R.id.etPlate) EditText etPlate;
    @BindView(R.id.etYear) EditText etYear;
    @BindView(R.id.etColor) EditText etColor;

    Vehicle vehicle;
    DBHandler db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_edit_vehicle_fragment, container, false);
        ButterKnife.bind(this, view);

        etPlate.addTextChangedListener(Mask.insert("###-####", etPlate));

        db = new DBHandler(getContext());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            vehicle  = db.getVehicle(bundle.getInt("vehicle", 1));
            etBrand.setText(vehicle.getBrand());
            etModel.setText(vehicle.getModel());
            if (!(vehicle.getLicensePlate() == null))
                etPlate.setText(Mask.unmask(vehicle.getLicensePlate()));
            etYear.setText(vehicle.getYear());
            etColor.setText(vehicle.getColor());
        }

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


    @OnClick(R.id.btnConfirm)
    public void confirm(){
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
            db.updateVehicle(this.vehicle);
        }

        goBack();
    }

    private boolean isValidField(EditText editText){
        return !editText.getText().toString().isEmpty();
    }

}