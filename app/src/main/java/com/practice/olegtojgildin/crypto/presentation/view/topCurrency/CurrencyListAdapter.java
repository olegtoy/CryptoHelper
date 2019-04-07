package com.practice.olegtojgildin.crypto.presentation.view.topCurrency;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.practice.olegtojgildin.crypto.R;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoin;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.TopCoin;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCurrency;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by olegtojgildin on 16/03/2019.
 */


public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.ViewHolder> {
    private OnClickListener mOnClickListener;
    private List<TopCoin> mCurrencyList;
    private Context mContext;

    public CurrencyListAdapter(Context context, List<TopCoin> newsItems) {
        this.mCurrencyList = newsItems;
        this.mContext = context;
    }

    public void setOnClickListener(OnClickListener onListener) {
        this.mOnClickListener = onListener;
    }

    @Override
    public int getItemCount() {
        return mCurrencyList.size();
    }

    public void setListNews(List<TopCoin> list) {
        mCurrencyList = list;
    }

    public TopCoin getCoinItem(int position) {
        return mCurrencyList.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_currency, parent, false);
        return new ViewHolder(view, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        TopCoin topCoin = getCoinItem(position);
        CryptoCoin cryptoCoin = topCoin.cryptoCoin;
        CryptoCurrency cryptoCurrency = topCoin.raw.cryptoCurrency;

        viewHolder.tvSymbol.setText(cryptoCoin.getName());
        viewHolder.tvName.setText(cryptoCoin.getFullName());
        viewHolder.tvPrice.setText(cryptoCurrency.getPrice());
        viewHolder.tvChange.setText(cryptoCurrency.getChangePercent());
        viewHolder.tvAlgorithm.setText(cryptoCoin.getAlgorithm());
        viewHolder.tvProofType.setText(cryptoCoin.getProofType());
        Picasso.get()
                .load(cryptoCoin.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(viewHolder.ivImage);
        setColor(viewHolder,cryptoCurrency);
    }

    public void setColor(ViewHolder viewHolder, CryptoCurrency cryptoCurrency) {
        if (cryptoCurrency.getChangePercent().charAt(0) == '-')
            viewHolder.tvChange.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        else  viewHolder.tvChange.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvSymbol;
        TextView tvName;
        TextView tvPrice;
        TextView tvChange;
        TextView tvAlgorithm;
        TextView tvProofType;

        ImageView ivImage;
        OnClickListener ocListener;

        public ViewHolder(View view, OnClickListener ocListener) {
            super(view);
            ivImage = view.findViewById(R.id.iv_image);
            tvSymbol = view.findViewById(R.id.tv_symbol);
            tvName = view.findViewById(R.id.tv_name);
            tvPrice = view.findViewById(R.id.tv_price);
            tvChange = view.findViewById(R.id.tv_changeValue);
            tvAlgorithm = view.findViewById(R.id.tv_algorithm);
            tvProofType = view.findViewById(R.id.tv_proofType);
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
