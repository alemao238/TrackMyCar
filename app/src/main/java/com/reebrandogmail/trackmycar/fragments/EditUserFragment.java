package com.reebrandogmail.trackmycar.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.reebrandogmail.trackmycar.R;
import com.reebrandogmail.trackmycar.Util.DBHandler;
import com.reebrandogmail.trackmycar.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by renan.brando on 09/08/2017.
 */

public class EditUserFragment extends Fragment {

    @BindView(R.id.etName)  EditText etUsername;
    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etPhone) EditText etPhone;

    User user;
    DBHandler db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_edit_user_fragment, container, false);
        ButterKnife.bind(this, view);

        db = new DBHandler(getContext());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            user  = db.getUser(bundle.getInt("user", 1));
            etUsername.setText(user.getUser());
        }

        return view;
    }

    @OnClick(R.id.btnCancel)
    public void cancel(View view){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_main, new ProfileFragment());
        transaction.disallowAddToBackStack();
        transaction.commit();
    }


    @OnClick(R.id.btnConfirm)
    public void confirm(View view){
        if (!isValidField(etUsername)){
            etUsername.setError("Invalid input");
        }
        /*else if (!isValidField(etEmail)){
            etUsername.setError("Invalid input");
        }
        else if (!isValidField(etPhone)){
            etPhone.setError("Invalid input");
        }*/
        else {
            user = new User();
            user.setUser(etUsername.getText().toString());
            //user.setMail(etEmail.getText().toString());
            //user.setPhone(etPhone.getText().toString());
            db.updateUser(user);
        }
    }

    private boolean isValidField(EditText editText){
        return !editText.getText().toString().isEmpty();
    }

}
