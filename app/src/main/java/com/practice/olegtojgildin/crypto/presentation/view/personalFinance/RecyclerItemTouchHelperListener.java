package com.practice.olegtojgildin.crypto.presentation.view.personalFinance;

import android.support.v7.widget.RecyclerView;

/**
 * Created by olegtojgildin on 12/04/2019.
 */

public interface RecyclerItemTouchHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction,int position);
}
