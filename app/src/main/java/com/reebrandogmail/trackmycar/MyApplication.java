package com.reebrandogmail.trackmycar;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by renanribeirobrando on 25/07/17.
 */

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
