package com.practice.olegtojgildin.crypto.presentation.view.topCurrency;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.practice.olegtojgildin.crypto.R;
import com.practice.olegtojgildin.crypto.data.api.RetrofitHelper;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.TopCoin;
import com.practice.olegtojgildin.crypto.presentation.view.chart.ChartFragment;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
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
    private ImageView imageView;
    private Button mOpenChart;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initCoin();
        loadCurrency();
        initListener();
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
        mChange.setText(coin.getCHANGEPCT24HOUR() + "%");
        mPrice.setText(coin.getPRICE());
        mName.setText(fromSymbol);
        mChanges.setText(coin.getCHANGE24HOUR());
        mVolume.setText(coin.getVOLUME24HOUR());
        mHigh.setText(coin.getHIGH24HOUR());
        mLow.setText(coin.getLOW24HOUR());
        mOpen.setText(coin.getOPENDAY());
        mMarketCap.setText(coin.getMKTCAP());
        mSupply.setText(coin.getSUPPLY());
        setColor();
        Picasso.get()
                .load(coin.getIMAGEURL())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(imageView);

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
        mOpenChart=view.findViewById(R.id.openChart);
        imageView=view.findViewById(R.id.iv_image);
    }

    public void initListener(){
        mOpenChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, ChartFragment.newInstance(fromSymbol,toSymbol));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    public void setColor() {
        if (mChange.getText().charAt(0) == '-')
            mChange.setTextColor(getResources().getColor(R.color.colorNegative));
        else mChange.setTextColor(getResources().getColor(R.color.colorPositive));

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

    public static CurrencyDetailFragment newInstance1(String fromSymbol, String toSymbol) {
        Bundle args = new Bundle();
        args.putString("to", toSymbol);
        args.putString("from", fromSymbol);
        CurrencyDetailFragment fragment = new CurrencyDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
