package com.reebrandogmail.trackmycar.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.reebrandogmail.trackmycar.MainActivity;
import com.reebrandogmail.trackmycar.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reebrandogmail.trackmycar.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by renan.brando on 06/07/2017.
 */

public class ProfileFragment extends Fragment {

    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.pager) ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_profile_fragment, container, false);
        ButterKnife.bind(this, view);

        final FloatingActionButton fab = ((MainActivity) getActivity()).getFloatingActionButton();

        if (fab != null) {
            ((MainActivity) getActivity()).hideFloatingActionButton();
        }

        tabLayout.addTab(tabLayout.newTab().setText(R.string.users));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.vehicles));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final PagerAdapter adapter = new PagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

}
