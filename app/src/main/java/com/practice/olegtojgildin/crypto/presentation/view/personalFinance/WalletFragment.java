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

public class WalletFragment extends Fragment implements WalletView, RecyclerItemTouchHelperListener {
    private FloatingActionButton mAddCurrencyFAB;
    private RecyclerView mRecyclerView;
    private WalletAdapter mAdapter;
    private List<CryptoCoinFullInfo> mFullList;
    private List<String> coinListName;
    // private List<CoinWithCount> mCoinList;
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

        updateFragment();

    }

    public void updateFragment() {
        walletPresenter.loadCoin();
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
        walletPresenter.loadCoin();
    }

    public void initView(View view) {
        mAddCurrencyFAB = (FloatingActionButton) view.findViewById(R.id.fabAddInWallet);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_wallet);
    }

    @Override
    public void setData(List<CryptoCoinFullInfo> newsList) {
        mAdapter.setListFull(newsList);
        mFullList=newsList;
        for (int i = 0; i < newsList.size(); i++)
            coinListName.add(newsList.get(i).display.crypto.cryptoCurrency.getNameCoin());
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
            mAdapter.removeCoin(deleteIndex);

        }
    }
}
