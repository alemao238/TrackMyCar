package com.reebrandogmail.trackmycar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.reebrandogmail.trackmycar.fragments.UserTab;
import com.reebrandogmail.trackmycar.fragments.VehicleTab;

/**
 * Created by renan.brando on 18/07/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                UserTab userTab = new UserTab();
                return userTab;
            case 1:
                VehicleTab vehicleTab = new VehicleTab();
                return vehicleTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
