package com.reebrandogmail.trackmycar.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.reebrandogmail.trackmycar.R;
import com.reebrandogmail.trackmycar.Util.DBHandler;
import com.reebrandogmail.trackmycar.model.History;

import java.util.List;

/**
 * Created by renan.brando on 11/09/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<History> historiesList;
    private Context mContext;
    private FragmentActivity fragmentActivity;

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView place, timestamp;
        private ImageView share;

        private MyViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            place = (TextView) view.findViewById(R.id.tvPlace);
            timestamp = (TextView) view.findViewById(R.id.tvTimeStamp);
            share = (ImageView) view.findViewById(R.id.share);
        }
    }


    public HistoryAdapter(FragmentActivity fragmentActivity, List<History> historiesList) {
        this.historiesList = historiesList;
        this.fragmentActivity = fragmentActivity;
    }

    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list_row, parent, false);

        return new HistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HistoryAdapter.MyViewHolder holder, int position) {
        final History history = historiesList.get(position);
        holder.place.setText(history.getMapsURL());
        holder.timestamp.setText(history.getTimestamp());

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, holder.share);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_history);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.facebook:
                                ShareDialog shareDialog = new ShareDialog(fragmentActivity);
                                ShareLinkContent content = new ShareLinkContent.Builder()
                                        .setContentUrl(Uri.parse(history.getMapsURL()))
                                        .build();
                                shareDialog.show(content);  // Show facebook ShareDialog
                                return true;
                            case R.id.google:

                                return true;
                            default:
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return historiesList.size();
    }

    // Generic method for swapping fragments in the activity with extra value
    private void swapFragmentsWithValue(int activity, Fragment fragment, String key, int value){
        Bundle bundle = new Bundle();
        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        bundle.putInt(key, value);
        fragment.setArguments(bundle);
        transaction.replace(activity, fragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

}