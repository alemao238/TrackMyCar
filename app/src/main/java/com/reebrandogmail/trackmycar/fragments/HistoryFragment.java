package com.reebrandogmail.trackmycar.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reebrandogmail.trackmycar.MainActivity;
import com.reebrandogmail.trackmycar.R;
import com.reebrandogmail.trackmycar.Util.DBHandler;
import com.reebrandogmail.trackmycar.adapter.DividerItemDecoration;
import com.reebrandogmail.trackmycar.adapter.HistoryAdapter;
import com.reebrandogmail.trackmycar.model.History;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by renan.brando on 06/07/2017.
 */

public class HistoryFragment extends Fragment {

    private List<History> historiesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HistoryAdapter mAdapter;
    private DBHandler db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_history_fragment, container, false);
        ButterKnife.bind(this, view);

        final FloatingActionButton fab = ((MainActivity) getActivity()).getFloatingActionButton();

        if (fab != null) {
            ((MainActivity) getActivity()).hideFloatingActionButton();
        }

        db = new DBHandler(view.getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.rvHistory);

        mAdapter = new HistoryAdapter(getActivity(), historiesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        prepareHistoriesData();

        return view;
    }

    private void prepareHistoriesData() {
        List<History> histories = db.getAllHistories();
        for (History history : histories){
            historiesList.add(history);
        }
        mAdapter.notifyDataSetChanged();
    }
}
