package com.reebrandogmail.trackmycar.fragments;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.reebrandogmail.trackmycar.MainActivity;
import com.reebrandogmail.trackmycar.R;
import com.reebrandogmail.trackmycar.Util.DBHandler;
import com.reebrandogmail.trackmycar.adapter.ItemAdapter;
import com.reebrandogmail.trackmycar.adapter.MapItemAdapter;
import com.reebrandogmail.trackmycar.model.History;
import com.reebrandogmail.trackmycar.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private MapItemAdapter adapter;
    private ItemAdapter adapter2;
    private List<User> usersList;
    private List<History> historiesList;
    private DBHandler db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_main_fragment, container, false);
        ButterKnife.bind(this, view);

        final FloatingActionButton fab = ((MainActivity) getActivity()).getFloatingActionButton();

        if (fab != null) {
            ((MainActivity) getActivity()).showFloatingActionButton();
        }

        db = new DBHandler(view.getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recycler_view2);

        usersList = new ArrayList<>();
        historiesList = new ArrayList<>();
        adapter = new MapItemAdapter(view.getContext(), historiesList);
        adapter2 = new ItemAdapter(view.getContext(), usersList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(view.getContext(), 2);
        recyclerView2.setLayoutManager(mLayoutManager2);
        recyclerView2.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(adapter2);

        prepareUsersData();

        return view;
    }

    private void prepareUsersData() {
        List<User> users = db.getAllUsers();
        List<History> histories = db.getAllHistories();
        for (User user : users){
            usersList.add(user);
        }
        for (History history : histories){
            historiesList.add(history);
        }
        adapter.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}

