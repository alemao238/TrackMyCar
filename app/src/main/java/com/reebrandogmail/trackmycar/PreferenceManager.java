package com.reebrandogmail.trackmycar;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by renanribeirobrando on 17/07/17.
 */

public class PreferenceManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "intro_slider-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String KEEP_CONNECTED = "keep_connected";

    public PreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setKeepConnected(boolean keepConnected){
        editor.putBoolean(KEEP_CONNECTED, keepConnected);
        editor.commit();
    }

    public boolean isConnected() {
        return pref.getBoolean(KEEP_CONNECTED, false);
    }
}

