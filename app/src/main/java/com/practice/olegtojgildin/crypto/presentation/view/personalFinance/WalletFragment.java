package com.practice.olegtojgildin.crypto.presentation.view.personalFinance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practice.olegtojgildin.crypto.R;
import com.practice.olegtojgildin.crypto.data.datastore.WebDataStoreImpl;
import com.practice.olegtojgildin.crypto.data.local.DbManager;
import com.practice.olegtojgildin.crypto.data.local.PersonalFinance.CoinWithCount;
import com.practice.olegtojgildin.crypto.data.local.PersonalFinance.DbManagerPersonal;
import com.practice.olegtojgildin.crypto.data.local.dataStore.DbDataStoreImpl;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;
import com.practice.olegtojgildin.crypto.data.repositories.FavoritesRepositoryImpl;
import com.practice.olegtojgildin.crypto.data.repositories.WalletRepositoryImpl;
import com.practice.olegtojgildin.crypto.domain.favorites.FavoritesInteractorImpl;
import com.practice.olegtojgildin.crypto.domain.wallet.WalletInteractorImpl;
import com.practice.olegtojgildin.crypto.presentation.presenter.FavoritesPresenter;
import com.practice.olegtojgildin.crypto.presentation.presenter.WalletPresenter;
import com.practice.olegtojgildin.crypto.presentation.view.favorites.FavoritesAdapter;
import com.practice.olegtojgildin.crypto.presentation.view.selectCurrency.SelectCurrencyActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olegtojgildin on 12/04/2019.
 */

public class WalletFragment extends Fragment implements WalletView {
    private FloatingActionButton mAddCurrencyFAB;
    private RecyclerView mRecyclerView;
    private WalletAdapter mAdapter;
    private List<CryptoCoinFullInfo> mFavoriteList;
    private List<String> coinListName;
    private List<CoinWithCount> mCoinList;
    private WalletPresenter walletPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        mCoinList=new ArrayList<>();
        coinListName=new ArrayList<>();
       // mFavoriteList = new ArrayList<>();
        //mFavoriteListString = new ArrayList<>();
        mAddCurrencyFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(AddCoinWithCountActivity.newIntent(getContext()), 1);
            }
        });
        walletPresenter = new WalletPresenter(new WalletInteractorImpl(new WalletRepositoryImpl(new WebDataStoreImpl(),new DbDataStoreImpl(getContext()))));
        walletPresenter.attachView(this);
        initRecyclerView();
        walletPresenter.loadCoin();

    }

    public void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setAutoMeasureEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new WalletAdapter(getContext());
        mAdapter.setListNews(mCoinList);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String name = data.getStringExtra("name");
            Double count = data.getDoubleExtra("count",0);
            addCurrency(name,count);
        }
    }

    private void addCurrency(String name,Double count) {
        DbManagerPersonal dbManager = new DbManagerPersonal(getContext());

        if(!coinListName.contains(name))
            dbManager.addCurrency(name,count);
        else {
            Log.d("UPDATE", "true");
            dbManager.updateCurrency(name,count);
        }

        mCoinList.clear();
        walletPresenter.loadCoin();
        mAdapter.notifyDataSetChanged();
    }

    public void initView(View view) {
        mAddCurrencyFAB = (FloatingActionButton) view.findViewById(R.id.fabAddInWallet);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_wallet);
    }

    @Override
    public void setData(CryptoCoinFullInfo newsList) {

    }

    @Override
    public void setDataWallet(List<CoinWithCount> list) {
        mCoinList=list;
        for(int i =0; i<list.size();i++)
            coinListName.add(list.get(i).getCoin());


        mAdapter.setListNews(list);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void showError() {

    }
}
