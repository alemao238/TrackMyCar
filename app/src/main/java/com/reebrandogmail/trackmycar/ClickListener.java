package com.reebrandogmail.trackmycar;

import android.view.View;

/**
 * Created by renan.brando on 28/07/2017.
 */


public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}