package com.reebrandogmail.trackmycar.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.reebrandogmail.trackmycar.R;
import com.reebrandogmail.trackmycar.Util.DBHandler;
import com.reebrandogmail.trackmycar.model.User;

import butterknife.ButterKnife;

/**
 * Created by renan.brando on 09/08/2017.
 */

public class EditUserFragment extends Fragment {

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
            Toast.makeText(getContext(), user.getUser(), Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}
