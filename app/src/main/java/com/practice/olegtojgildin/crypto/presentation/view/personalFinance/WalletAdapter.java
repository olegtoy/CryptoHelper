package com.practice.olegtojgildin.crypto.presentation.view.personalFinance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.practice.olegtojgildin.crypto.R;
import com.practice.olegtojgildin.crypto.data.local.PersonalFinance.CoinWithCount;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;
import com.practice.olegtojgildin.crypto.presentation.view.favorites.FavoritesAdapter;
import com.practice.olegtojgildin.crypto.presentation.view.topCurrency.CurrencyListAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by olegtojgildin on 12/04/2019.
 */

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {
    //private CurrencyListAdapter.OnClickListener mOnClickListener;
    private List<CoinWithCount> mCurrencyList;
    private List<CryptoCoinFullInfo> mFullInfo;

    private Context mContext;

    public WalletAdapter(Context context) {
        this.mContext = context;
    }


    @Override
    public int getItemCount() {
        return mCurrencyList.size();
    }

    public void setListNews(List<CoinWithCount> list) {
        mCurrencyList = list;
    }

    public void setListFullInfo(List<CryptoCoinFullInfo> list ){
        mFullInfo=list;
    }

    public CoinWithCount getCoinItem(int position) {
        return mCurrencyList.get(position);
    }

    @Override
    public WalletAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_wallet, parent, false);
        return new WalletAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WalletAdapter.ViewHolder viewHolder, int position) {
        CoinWithCount coinWithCount = mCurrencyList.get(position);
        viewHolder.tvName.setText(coinWithCount.getCoin());
        viewHolder.tvCount.setText(Double.toString(coinWithCount.getCount()));

     /*   Picasso.get()
                .load(cryptoCoin.getIMAGEURL())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(viewHolder.ivImage);*/
    }
    public void removeCoin(int index){
        mCurrencyList.remove(index);
        notifyItemRemoved(index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCount;
        TextView tvName;
        View viewBackground,viewForeground;
        //TextView tvPrice;

        ImageView ivImage;
        //  CurrencyListAdapter.OnClickListener ocListener;

        public ViewHolder(View view) {
            super(view);
            ivImage = view.findViewById(R.id.imageCoin);
            tvName = view.findViewById(R.id.nameCount);
            tvCount = view.findViewById(R.id.countCoin);
            viewBackground=view.findViewById(R.id.view_bachground);
            viewForeground=view.findViewById(R.id.view_foreground);

            //tvPrice = view.findViewById(R.id.tv_price);
        }

   /*     @Override
        public void onClick(View view) {
            if (ocListener != null) {
                ocListener.onClick(view, getAdapterPosition());
            }
        }*/

    }

    public interface OnClickListener {
        void onClick(View view, int position);
    }

}
