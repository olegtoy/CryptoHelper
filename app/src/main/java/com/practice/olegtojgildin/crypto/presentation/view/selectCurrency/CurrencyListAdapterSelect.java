package com.practice.olegtojgildin.crypto.presentation.view.selectCurrency;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.practice.olegtojgildin.crypto.R;
import com.practice.olegtojgildin.crypto.data.models.news.CryptoNewsArticle;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoin;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCurrency;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.TopCoin;
import com.practice.olegtojgildin.crypto.presentation.view.news.NewsListAdapter;
import com.practice.olegtojgildin.crypto.presentation.view.topCurrency.CurrencyListAdapter;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

/**
 * Created by olegtojgildin on 08/04/2019.
 */

public class CurrencyListAdapterSelect extends RecyclerView.Adapter<CurrencyListAdapterSelect.ViewHolder> {
    private OnClickListener mOnClickListener;
    private List<String> mCurrecncyList;
    private Context mContext;


    public CurrencyListAdapterSelect(Context context) {
        mContext = context;
        mCurrecncyList = Arrays.asList(mContext.getResources().getStringArray(R.array.crypto1));
    }

    public void setOnClickListener(OnClickListener onListener) {
        this.mOnClickListener = onListener;
    }

    public String getCurrency(int position) {
        return mCurrecncyList.get(position);
    }

    @Override
    public CurrencyListAdapterSelect.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_currency_select, parent, false);
        return new CurrencyListAdapterSelect.ViewHolder(view, mOnClickListener);
    }


    @Override
    public int getItemCount() {
        return mCurrecncyList.size();
    }

    @Override
    public void onBindViewHolder(CurrencyListAdapterSelect.ViewHolder viewHolder, int position) {
        viewHolder.name.setText(mCurrecncyList.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        OnClickListener ocListener;

        public ViewHolder(View view, CurrencyListAdapterSelect.OnClickListener ocListener) {
            super(view);
            name = view.findViewById(R.id.name_curr);
            this.ocListener = ocListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (ocListener != null) {
                ocListener.onClick(view, getAdapterPosition());
            }
        }

    }

    public interface OnClickListener {
        void onClick(View view, int position);
    }
}
