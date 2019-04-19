package com.practice.olegtojgildin.crypto.presentation.view.topCurrency;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.practice.olegtojgildin.crypto.R;
import com.practice.olegtojgildin.crypto.data.datastore.web.WebDataStoreImpl;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.TopCoin;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinList;
import com.practice.olegtojgildin.crypto.data.repositories.TopCurrencyRepositoryImpl;
import com.practice.olegtojgildin.crypto.domain.topCurrency.TopCurrencyInteractorImpl;
import com.practice.olegtojgildin.crypto.presentation.presenter.TopCurrencyPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olegtojgildin on 26/03/2019.
 */

public class CurrencyFragment extends Fragment implements TopCurrencyView {

    private List<TopCoin> mCurrencyList;
    private RecyclerView mRecyclerView;
    private CurrencyListAdapter mCurrencyAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Spinner mSpinnerCategory;
    private String mCoin = "USD";
    private TopCurrencyPresenter topCurrencyPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_currency, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initRecyclerView();
        initSpinnner();
        initListener();

        topCurrencyPresenter=new TopCurrencyPresenter(new TopCurrencyInteractorImpl(new TopCurrencyRepositoryImpl(new WebDataStoreImpl())));
        topCurrencyPresenter.attachView(this);
        topCurrencyPresenter.loadTopCurrency(Integer.toString(20), mCoin);

    }

    public void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> updateActivity());

        mCurrencyAdapter.setOnClickListener(new CurrencyListAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                CurrencyFragment.this.openDetailCurrency(mCurrencyList.get(position));
            }
        });
    }

    public void initSpinnner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.coin, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCategory.setAdapter(adapter);
        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mCoin=(String) adapterView.getItemAtPosition(i);
                updateActivity();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void updateActivity() {
        mCurrencyList.clear();
        topCurrencyPresenter.loadTopCurrency(Integer.toString(20), mCoin);
        mCurrencyAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }


    public void initView(View view) {
        mRecyclerView = view.findViewById(R.id.rv_currency);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        mSpinnerCategory = view.findViewById(R.id.coin_spinner);
    }

    public void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setAutoMeasureEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);
        mCurrencyList = new ArrayList<>();
        mCurrencyAdapter = new CurrencyListAdapter(getContext(), mCurrencyList);
        mRecyclerView.setAdapter(mCurrencyAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void openDetailCurrency(TopCoin topCoin){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, CurrencyDetailFragment.newInstance(topCoin,mCoin));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void setData(CryptoCoinList newsList) {
        mCurrencyList=newsList.getItems();
        mCurrencyAdapter.setListNews(newsList.getItems());
        mCurrencyAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }
}
