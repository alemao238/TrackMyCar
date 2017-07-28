package com.reebrandogmail.trackmycar.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reebrandogmail.trackmycar.R;
import com.reebrandogmail.trackmycar.model.User;

import java.util.List;

/**
 * Created by renan.brando on 27/07/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<User> usersList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id, username, password;

        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.tvUSerId);
            username = (TextView) view.findViewById(R.id.tvName);
            password = (TextView) view.findViewById(R.id.tvPassword);
        }
    }


    public UserAdapter(List<User> usersList) {
        this.usersList = usersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User user = usersList.get(position);
//        holder.id.setText(user.getId());
        holder.username.setText(user.getUser());
        holder.password.setText(user.getPassword());
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}
