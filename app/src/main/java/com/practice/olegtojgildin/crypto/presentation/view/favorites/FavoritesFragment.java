package com.practice.olegtojgildin.crypto.presentation.view.favorites;

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

import com.practice.olegtojgildin.crypto.MainActivity;
import com.practice.olegtojgildin.crypto.R;
import com.practice.olegtojgildin.crypto.data.datastore.WebDataStoreImpl;
import com.practice.olegtojgildin.crypto.data.local.DbManager;
import com.practice.olegtojgildin.crypto.data.local.dataStore.DbDataStoreImpl;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.TopCoin;
import com.practice.olegtojgildin.crypto.data.repositories.ConvertRepositoryImpl;
import com.practice.olegtojgildin.crypto.data.repositories.FavoritesRepositoryImpl;
import com.practice.olegtojgildin.crypto.data.repositories.NewsRepositoryImpl;
import com.practice.olegtojgildin.crypto.data.repositories.TopCurrencyRepositoryImpl;
import com.practice.olegtojgildin.crypto.domain.convert.ConvertorInteractorImpl;
import com.practice.olegtojgildin.crypto.domain.favorites.FavoritesInteractorImpl;
import com.practice.olegtojgildin.crypto.domain.news.NewsInteractorImpl;
import com.practice.olegtojgildin.crypto.domain.topCurrency.TopCurrencyInteractor;
import com.practice.olegtojgildin.crypto.domain.topCurrency.TopCurrencyInteractorImpl;
import com.practice.olegtojgildin.crypto.presentation.presenter.FavoritesPresenter;
import com.practice.olegtojgildin.crypto.presentation.presenter.NewsPresenter;
import com.practice.olegtojgildin.crypto.presentation.view.selectCurrency.CurrencyListAdapterSelect;
import com.practice.olegtojgildin.crypto.presentation.view.selectCurrency.SelectCurrencyActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olegtojgildin on 26/03/2019.
 */

public class FavoritesFragment extends Fragment implements FavoritesView {
    private FloatingActionButton mAddCurrencyFAB;
    private RecyclerView mRecyclerView;
    private FavoritesAdapter mAdapter;
    private List<CryptoCoinFullInfo> mFavoriteList;
    private List<String> mFavoriteListString;
    private FavoritesPresenter newsPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        mFavoriteList = new ArrayList<>();
        mFavoriteListString = new ArrayList<>();
        mAddCurrencyFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(SelectCurrencyActivity.newIntent(getContext()), 1);
            }
        });
        newsPresenter = new FavoritesPresenter(new FavoritesInteractorImpl(new FavoritesRepositoryImpl(new WebDataStoreImpl(),new DbDataStoreImpl(getContext()))));
        newsPresenter.attachView(this);
        initRecyclerView();
        newsPresenter.loadCoin();

    }


    public void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setAutoMeasureEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new FavoritesAdapter(getContext());
        mAdapter.setListNews(mFavoriteList);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String name = data.getStringExtra("name");
            addCurrency(name);
        }
    }

    private void addCurrency(String name) {
        DbManager dbManager = new DbManager(getContext());
        dbManager.addCurrency(name);
        mFavoriteListString.clear();
        mFavoriteList.clear();
        newsPresenter.loadCoin();
    }

    public void initView(View view) {
        mAddCurrencyFAB = (FloatingActionButton) view.findViewById(R.id.fabAddCurrency);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_favorites);
    }


    @Override
    public void setData(CryptoCoinFullInfo newsList) {
        mFavoriteList.add(newsList);
        mAdapter.setListNews(mFavoriteList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setDataFavoriteList(List<String> list) {
        mFavoriteListString = list;
        newsPresenter.loadNewsList(mFavoriteListString);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

}
