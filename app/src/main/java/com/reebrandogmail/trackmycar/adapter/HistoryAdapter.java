package com.reebrandogmail.trackmycar.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.reebrandogmail.trackmycar.R;
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
                            case R.id.other:
                                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                                share.setType("text/plain");
                                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                                // Add data to the intent, the receiving app will decide
                                // what to do with it.
                                share.putExtra(Intent.EXTRA_SUBJECT, "I was driving here.");
                                share.putExtra(Intent.EXTRA_TEXT, history.getMapsURL());

                                fragmentActivity.startActivity(Intent.createChooser(share, "Share your location!"));
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


}