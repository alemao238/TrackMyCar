package com.reebrandogmail.trackmycar.adapter;

import android.content.Context;
import android.content.DialogInterface;
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

import com.reebrandogmail.trackmycar.MainActivity;
import com.reebrandogmail.trackmycar.R;
import com.reebrandogmail.trackmycar.Util.DBHandler;
import com.reebrandogmail.trackmycar.Util.Mask;
import com.reebrandogmail.trackmycar.fragments.EditUserFragment;
import com.reebrandogmail.trackmycar.model.User;

import java.util.List;


/**
 * Created by renan.brando on 27/07/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<User> usersList;
    private Context mContext;
    private FragmentActivity fragmentActivity;

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView username, phone;
        private ImageView overflow;

        private MyViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            username = (TextView) view.findViewById(R.id.tvName);
            phone = (TextView) view.findViewById(R.id.tvPhone);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public UserAdapter(FragmentActivity fragmentActivity, List<User> usersList) {
        this.usersList = usersList;
        this.fragmentActivity = fragmentActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final User user = usersList.get(position);
        holder.username.setText(user.getUser());
        holder.phone.setText(Mask.mask("(##)#####-####", user.getPhone()));

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, holder.overflow);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_profile);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_add_favourite:
                                swapFragmentsWithValue(R.id.fragment_main, new EditUserFragment(), "user", user.getId());
                                return true;
                            case R.id.action_play_next:
                                AlertDialog.Builder builder;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
                                } else {
                                    builder = new AlertDialog.Builder(mContext);
                                }
                                builder.setTitle("Delete")
                                        .setMessage("Are you sure you want to delete " + user.getUser() + "?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // delete
                                                DBHandler db = new DBHandler(mContext);
                                                db.deleteUser(user);

                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
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
        return usersList.size();
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
