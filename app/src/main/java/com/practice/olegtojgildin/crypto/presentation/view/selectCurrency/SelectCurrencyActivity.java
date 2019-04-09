package com.practice.olegtojgildin.crypto.presentation.view.selectCurrency;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.practice.olegtojgildin.crypto.R;
import com.practice.olegtojgildin.crypto.presentation.view.news.NewsListAdapter;

import java.util.ArrayList;

/**
 * Created by olegtojgildin on 08/04/2019.
 */

public class SelectCurrencyActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CurrencyListAdapterSelect mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_currency);
        initView();
        initRecyclerView();
        initListener();
    }

    public void initView(){
        mRecyclerView=(RecyclerView)findViewById(R.id.rv_select_currency);
    }
    public void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setAutoMeasureEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new CurrencyListAdapterSelect(getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context,SelectCurrencyActivity.class);
    }

    public void initListener(){
        mAdapter.setOnClickListener(new CurrencyListAdapterSelect.OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("name", mAdapter.getCurrency(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }



}
