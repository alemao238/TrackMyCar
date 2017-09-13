package com.reebrandogmail.trackmycar.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

/**
 * Created by renan.brando on 13/09/2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    public void onTokenRefresh() {

        //pega o token do usuário
        super.onTokenRefresh();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshToken);
    }

    private void sendRegistrationToServer(String refreshToken) {
        Log.d(TAG, "Refreshed Token:" + refreshToken);
    }
}