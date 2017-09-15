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
import com.reebrandogmail.trackmycar.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by renan.brando on 15/09/2017.
 */

public class AddUserFragment  extends Fragment {

    @BindView(R.id.etNameA) EditText etUsername;
    @BindView(R.id.etEmailA) EditText etEmail;
    @BindView(R.id.etPhoneA) EditText etPhone;

    User user;
    DBHandler db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_add_user_fragment, container, false);
        ButterKnife.bind(this, view);

        etPhone.addTextChangedListener(Mask.insert("(##)#####-####", etPhone));

        db = new DBHandler(getContext());

        user = new User();

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


    @OnClick(R.id.btnConfirmAddUser)
    public void submitUser(){
        if (!isValidField(etUsername)){
            etUsername.setError(getString(R.string.invalid_input));
        }
        else if (!isValidField(etEmail)){
            etEmail.setError(getString(R.string.invalid_input));
        }
        else if (!isValidField(etPhone)){
            etPhone.setError(getString(R.string.invalid_input));
        }
        else {
            this.user.setUser(etUsername.getText().toString());
            this.user.setMail(etEmail.getText().toString());
            this.user.setPhone(etPhone.getText().toString());
            db.addUser(this.user);
            goBack();
        }

    }

    private boolean isValidField(EditText editText){
        return !editText.getText().toString().isEmpty();
    }

}