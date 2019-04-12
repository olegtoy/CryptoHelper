package com.practice.olegtojgildin.crypto.presentation.view.favorites;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.practice.olegtojgildin.crypto.R;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoin;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCurrency;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.TopCoin;
import com.practice.olegtojgildin.crypto.presentation.view.topCurrency.CurrencyListAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by olegtojgildin on 08/04/2019.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private CurrencyListAdapter.OnClickListener mOnClickListener;
    private List<CryptoCoinFullInfo> mCurrencyList;
    private Context mContext;

    public FavoritesAdapter(Context context) {
        this.mContext = context;
    }

    public void setOnClickListener(CurrencyListAdapter.OnClickListener onListener) {
        this.mOnClickListener = onListener;
    }

    @Override
    public int getItemCount() {
        return mCurrencyList.size();
    }

    public void setListNews(List<CryptoCoinFullInfo> list) {
        mCurrencyList = list;
    }

    public CryptoCoinFullInfo getCoinItem(int position) {
        return mCurrencyList.get(position);
    }

    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_favorites, parent, false);
        return new FavoritesAdapter.ViewHolder(view, mOnClickListener);
    }
    public void remove(int index){
        mCurrencyList.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.ViewHolder viewHolder, int position) {
        CryptoCoinFullInfo topCoin = getCoinItem(position);
        CryptoCoinFullInfo.Display.Crypto.Coin cryptoCoin = topCoin.display.crypto.cryptoCurrency;
        viewHolder.tvSymbol.setText(cryptoCoin.getFROMSYMBOL());
        viewHolder.tvName.setText(cryptoCoin.getFROMSYMBOL());
        viewHolder.tvPrice.setText(cryptoCoin.getPRICE());
        viewHolder.tvChange.setText(cryptoCoin.getCHANGEPCTDAY()+ " %");
        viewHolder.tvAlgorithm.setText(cryptoCoin.getMARKET());
        Picasso.get()
                .load(cryptoCoin.getIMAGEURL())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(viewHolder.ivImage);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvSymbol;
        TextView tvName;
        TextView tvPrice;
        TextView tvChange;
        TextView tvAlgorithm;
        TextView tvProofType;
        View viewBackground,viewForeground;

        ImageView ivImage;
        CurrencyListAdapter.OnClickListener ocListener;

        public ViewHolder(View view, CurrencyListAdapter.OnClickListener ocListener) {
            super(view);
            ivImage = view.findViewById(R.id.iv_image);
            tvSymbol = view.findViewById(R.id.tv_symbol);
            tvName = view.findViewById(R.id.tv_name);
            tvPrice = view.findViewById(R.id.tv_price);
            tvChange = view.findViewById(R.id.tv_changeValue);
            tvAlgorithm = view.findViewById(R.id.tv_algorithm);
            tvProofType = view.findViewById(R.id.tv_proofType);
            viewBackground=view.findViewById(R.id.view_bachground1);
            viewForeground=view.findViewById(R.id.view_foreground1);

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
