package com.practice.olegtojgildin.crypto.presentation.view.personalFinance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.practice.olegtojgildin.crypto.R;
import com.practice.olegtojgildin.crypto.data.datastore.WebDataStoreImpl;
import com.practice.olegtojgildin.crypto.data.local.PersonalFinance.DbManagerPersonal;
import com.practice.olegtojgildin.crypto.data.local.dataStore.DbDataStoreImpl;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;
import com.practice.olegtojgildin.crypto.data.repositories.WalletRepositoryImpl;
import com.practice.olegtojgildin.crypto.domain.wallet.WalletInteractorImpl;
import com.practice.olegtojgildin.crypto.presentation.presenter.WalletPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olegtojgildin on 12/04/2019.
 */

public class WalletFragment extends Fragment implements WalletView, RecyclerItemTouchHelperListener {
    private FloatingActionButton mAddCurrencyFAB;
    private RecyclerView mRecyclerView;
    private WalletAdapter mAdapter;
    private List<CryptoCoinFullInfo> mFullList;
    private List<String> coinListName;
    // private List<CoinWithCount> mCoinList;
    private WalletPresenter walletPresenter;
    private Spinner mSpinnerCategory;
    private String mCoin = "USD";
    private TextView mSumWallet_tv;
    private double mSumWallet;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        //mCoinList = new ArrayList<>();
        coinListName = new ArrayList<>();
        mFullList = new ArrayList<>();

        mAddCurrencyFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(AddCoinWithCountActivity.newIntent(getContext()), 1);
            }
        });
        walletPresenter = new WalletPresenter(new WalletInteractorImpl(new WalletRepositoryImpl(new WebDataStoreImpl(), new DbDataStoreImpl(getContext()))));
        walletPresenter.attachView(this);
        initRecyclerView();

        initSpinnner();
        //updateFragment();

    }

    public void initSpinnner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.coin, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCategory.setAdapter(adapter);
        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mCoin = (String) adapterView.getItemAtPosition(i);
                updateFragment();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void updateFragment() {
        walletPresenter.loadCoin(mCoin);
    }


    public void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setAutoMeasureEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new WalletAdapter(getContext());
        mAdapter.setListFull(mFullList);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new WalletItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(mRecyclerView);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String name = data.getStringExtra("name");
            Double count = data.getDoubleExtra("count", 0);
            addCurrency(name, count);
        }
    }

    private void addCurrency(String name, Double count) {
        DbManagerPersonal dbManager = DbManagerPersonal.getInstance(getContext());

        if (!coinListName.contains(name))
            dbManager.addCurrency(name, count);
        else {
            Log.d("UPDATE", "true");
            dbManager.updateCurrency(name, count);
        }
        walletPresenter.loadCoin(mCoin);
    }

    public void initView(View view) {
        mAddCurrencyFAB = (FloatingActionButton) view.findViewById(R.id.fabAddInWallet);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_wallet);
        mSpinnerCategory = view.findViewById(R.id.wallet_spinner);
        mSumWallet_tv =view.findViewById(R.id.sum_Wallet);
    }

    @Override
    public void setData(List<CryptoCoinFullInfo> newsList) {
        mAdapter.setListFull(newsList);
        mFullList = newsList;
        mSumWallet=0;
        for (int i = 0; i < newsList.size(); i++)
        {
            coinListName.add(newsList.get(i).display.crypto.cryptoCurrency.getNameCoin());
            CryptoCoinFullInfo cryptoCoinFullInfo = newsList.get(i);
            double count = cryptoCoinFullInfo.display.crypto.cryptoCurrency.getCountCoin();
            double price = Double.parseDouble(cryptoCoinFullInfo.raw.crypto.cryptoCurrency.getPRICE());
            mSumWallet+=count*price;
        }
        mSumWallet_tv.setText(String.format("%.2f",mSumWallet)+ " " +mCoin);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof WalletAdapter.ViewHolder) {
            CryptoCoinFullInfo item = mFullList.get(viewHolder.getAdapterPosition());

            DbManagerPersonal dbManager = DbManagerPersonal.getInstance(getContext());
            dbManager.deleteNote(item.display.crypto.cryptoCurrency.getNameCoin());

            int deleteIndex = viewHolder.getAdapterPosition();

            double count = item.display.crypto.cryptoCurrency.getCountCoin();
            double price = Double.parseDouble(item.raw.crypto.cryptoCurrency.getPRICE());
            mSumWallet-=count*price;
            mSumWallet_tv.setText(String.format("%.2f",mSumWallet)+ " " +mCoin);

            mAdapter.removeCoin(deleteIndex);

        }
    }
}
