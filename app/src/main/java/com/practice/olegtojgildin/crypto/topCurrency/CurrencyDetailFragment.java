package com.practice.olegtojgildin.crypto.topCurrency;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.practice.olegtojgildin.crypto.R;
import com.practice.olegtojgildin.crypto.net.RetrofitHelper;
import com.practice.olegtojgildin.crypto.news.CryptoNewsArticle;
import com.practice.olegtojgildin.crypto.news.NewsDetailFragment;
import com.practice.olegtojgildin.crypto.news.NewsList;
import com.squareup.picasso.Picasso;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olegtojgildin on 28/03/2019.
 */

public class CurrencyDetailFragment extends Fragment {

    private CryptoCoinFullInfo mCryptoCoin;
    private String toSymbol;
    private String fromSymbol;
    private TextView mPrice;
    private TextView mChange;
    private TextView mName;
    private TextView mChanges;
    private TextView mVolume;
    private TextView mHigh;
    private TextView mLow;
    private TextView mOpen;
    private TextView mMarketCap;
    private TextView mSupply;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initCoin();
        loadCurrency();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_currency_detail, container, false);
    }

    public void initCoin() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            toSymbol = bundle.getString("to");
            fromSymbol = bundle.getString("from");
        } else {
            toSymbol = "USD";
            fromSymbol = "BTC";
        }
    }

    public void putCoinInfo(CryptoCoinFullInfo cryptoCoinFullInfo) {
        CryptoCoinFullInfo.Display.Crypto.Coin coin = cryptoCoinFullInfo.getDisplay().getCrypto().getCryptoCurrency();
        mChange.setText(coin.getCHANGEPCTDAY() + "%");
        mPrice.setText(coin.getPRICE());
        mName.setText(fromSymbol);
        mChanges.setText(coin.getCHANGEDAY());
        mVolume.setText(coin.getVOLUME24HOUR());
        mHigh.setText(coin.getHIGHDAY());
        mLow.setText(coin.getLOWDAY());
        mOpen.setText(coin.getOPENDAY());
        mMarketCap.setText(coin.getMKTCAP());
        mSupply.setText(coin.getSUPPLY());
        setColor();

    }

    public void initView(View view) {
        mName = view.findViewById(R.id.tv_name);
        mPrice = view.findViewById(R.id.tv_price);
        mChange = view.findViewById(R.id.tv_changePercent);
        mChanges = view.findViewById(R.id.tv_changeValue);
        mVolume = view.findViewById(R.id.tv_volume);
        mHigh = view.findViewById(R.id.tv_high);
        mLow = view.findViewById(R.id.tv_low);
        mOpen = view.findViewById(R.id.tv_open);
        mMarketCap = view.findViewById(R.id.tv_marketCap);
        mSupply = view.findViewById(R.id.tv_supply);
    }

    public void setColor() {
        if (mChange.getText().charAt(0) == '-')
            mChange.setTextColor(getResources().getColor(R.color.colorAccent));
        else mChange.setTextColor(getResources().getColor(R.color.colorPrimary));

    }

    public void loadCurrency() {
        new RetrofitHelper().getService()
                .getCoinsInfo(fromSymbol, toSymbol)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::putCoinInfo);
    }

    public static CurrencyDetailFragment newInstance(TopCoin topCoin, String toCoin) {
        Bundle args = new Bundle();
        args.putString("to", toCoin);
        args.putString("from", topCoin.cryptoCoin.getName());
        CurrencyDetailFragment fragment = new CurrencyDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
