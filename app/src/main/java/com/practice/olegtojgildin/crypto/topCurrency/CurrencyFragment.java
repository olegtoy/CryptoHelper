package com.practice.olegtojgildin.crypto.topCurrency;

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
import com.practice.olegtojgildin.crypto.net.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olegtojgildin on 26/03/2019.
 */

public class CurrencyFragment extends Fragment {

    private List<TopCoin> mCurrencyList;
    private RecyclerView mRecyclerView;
    private CurrencyListAdapter mCurrencyAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Spinner mSpinnerCategory;
    private String mCoin = "USD";

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
        loadCurrency();
        initSpinnner();
        initListener();
    }

    public void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> updateActivity());

        mCurrencyAdapter.setOnClickListener((view, position) -> openDetailCurrency(mCurrencyList.get(position)));
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
        loadCurrency();
    }

    public void loadCurrency() {
        new RetrofitHelper().getService()
                .getTopVolume(Integer.toString(20), mCoin)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CryptoCoinList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(CryptoCoinList newsList) {
                        mCurrencyList = newsList.getItems();
                        mCurrencyAdapter.setListNews(mCurrencyList);
                        mCurrencyAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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

}
