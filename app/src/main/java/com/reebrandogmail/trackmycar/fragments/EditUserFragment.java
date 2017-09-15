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
 * Created by renan.brando on 11/09/2017.
 */

public class EditUserFragment extends Fragment {

    @BindView(R.id.etName) EditText etUsername;
    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etPhone) EditText etPhone;

    User user;
    DBHandler db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_edit_user_fragment, container, false);
        ButterKnife.bind(this, view);

        etPhone.addTextChangedListener(Mask.insert("(##)#####-####", etPhone));

        db = new DBHandler(getContext());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            user  = db.getUser(bundle.getInt("user", 1));
            etUsername.setText(user.getUser());
            etEmail.setText(user.getMail());
            if (!(user.getPhone() == null))
                etPhone.setText(Mask.unmask(user.getPhone()));
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
            db.updateUser(this.user);
            goBack();
        }

    }

    private boolean isValidField(EditText editText){
        return !editText.getText().toString().isEmpty();
    }

}