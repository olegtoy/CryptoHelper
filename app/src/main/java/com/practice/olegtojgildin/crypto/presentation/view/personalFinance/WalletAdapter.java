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

    private List<CryptoCoinFullInfo> mFullInfo;

    private Context mContext;

    public WalletAdapter(Context context) {
        this.mContext = context;
    }


    @Override
    public int getItemCount() {
        return mFullInfo.size();
    }


    public void setListFull(List<CryptoCoinFullInfo> listFull) {
        mFullInfo = listFull;
        notifyDataSetChanged();
    }


    public CryptoCoinFullInfo getCoinItem(int position) {
        return mFullInfo.get(position);
    }

    @Override
    public WalletAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_wallet, parent, false);
        return new WalletAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WalletAdapter.ViewHolder viewHolder, int position) {
        CryptoCoinFullInfo cryptoCoinFullInfo = mFullInfo.get(position);
        double count = cryptoCoinFullInfo.display.crypto.cryptoCurrency.getCountCoin();
        double price = Double.parseDouble(cryptoCoinFullInfo.raw.crypto.cryptoCurrency.getPRICE());

        viewHolder.tvName.setText(cryptoCoinFullInfo.display.crypto.cryptoCurrency.getNameCoin());
        viewHolder.tvCount.setText(Double.toString(count) +" "+cryptoCoinFullInfo.display.crypto.cryptoCurrency.getFROMSYMBOL());
        viewHolder.tvPrice.setText(String.format("%.2f", price));
        viewHolder.tvSum.setText(String.format( "%.2f", count*price) + cryptoCoinFullInfo.display.crypto.cryptoCurrency.getTOSYMBOL());
        if (mFullInfo.size() != 0)
            Picasso.get()
                    .load(mFullInfo.get(position).display.crypto.cryptoCurrency.getIMAGEURL())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(viewHolder.ivImage);
    }

    public void removeCoin(int index) {
        mFullInfo.remove(index);
        notifyItemRemoved(index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCount;
        TextView tvName;
        View viewBackground, viewForeground;
        TextView tvPrice;
        TextView tvSum;

        ImageView ivImage;
        //  CurrencyListAdapter.OnClickListener ocListener;

        public ViewHolder(View view) {
            super(view);
            ivImage = view.findViewById(R.id.imageCoin);
            tvName = view.findViewById(R.id.nameCount);
            tvCount = view.findViewById(R.id.countCoin);
            viewBackground = view.findViewById(R.id.view_bachground);
            viewForeground = view.findViewById(R.id.view_foreground);
            tvPrice = view.findViewById(R.id.price_tv);
            tvSum = view.findViewById(R.id.sum_tv);
        }


    }

    public interface OnClickListener {
        void onClick(View view, int position);
    }

}
