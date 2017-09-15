package com.reebrandogmail.trackmycar.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.reebrandogmail.trackmycar.R;
import com.reebrandogmail.trackmycar.model.History;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by renan.brando on 15/09/2017.
 */

public class MapItemAdapter extends RecyclerView.Adapter<MapItemAdapter.MyViewHolder> implements OnMapReadyCallback {

    private Context mContext;
    private List<History> historiesList;
    private HashSet<MapView> mapViews = new HashSet<>();
    private GoogleMap googleMap;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new
                LatLng(51.0107214,6.9549693), 10));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.0107214, 6.9549693)));
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title, count, thumbnail;
        public ImageView overflow;
        public  MapView mapView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (TextView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            mapView = (MapView) view.findViewById(R.id.map_view);
            //mapView = ((MapView) view.findViewById(R.id.map_view)).getMap();

        }

    }


    public MapItemAdapter(Context mContext, List<History> historiesList) {
        this.mContext = mContext;
        this.historiesList = historiesList;
        //mapView.getMapAsync(this);
    }

    public MapItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);

        return new MapItemAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MapItemAdapter.MyViewHolder holder, int position) {
        History history = historiesList.get(position);
        //holder.mapView.onCreate();
        //holder.mapView.getMapAsync(this);
        //holder.title.setText(history.getUser());
        //holder.count.setText(user.getPhone() + " " + mContext.getString(R.string.granted));
        //holder.thumbnail.setText(String.valueOf(user.getUser().charAt(0)));
        // loading album cover using Glide library
        //Glide.with(mContext).load(R.drawable.map).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_item, popup.getMenu());
        popup.setOnMenuItemClickListener(new MapItemAdapter.MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_more:
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return historiesList.size();
    }
}
