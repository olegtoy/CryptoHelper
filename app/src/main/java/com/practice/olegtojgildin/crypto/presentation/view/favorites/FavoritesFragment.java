package com.practice.olegtojgildin.crypto.presentation.view.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.practice.olegtojgildin.crypto.R;
import com.practice.olegtojgildin.crypto.data.datastore.web.WebDataStoreImpl;
import com.practice.olegtojgildin.crypto.data.local.favorites.DbManager;
import com.practice.olegtojgildin.crypto.data.local.dataStore.DbDataStoreImpl;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;
import com.practice.olegtojgildin.crypto.data.repositories.FavoritesRepositoryImpl;
import com.practice.olegtojgildin.crypto.domain.favorites.FavoritesInteractorImpl;
import com.practice.olegtojgildin.crypto.presentation.presenter.FavoritesPresenter;
import com.practice.olegtojgildin.crypto.presentation.view.personalFinance.RecyclerItemTouchHelperListener;
import com.practice.olegtojgildin.crypto.presentation.view.selectCurrency.SelectCurrencyActivity;
import com.practice.olegtojgildin.crypto.presentation.view.topCurrency.CurrencyDetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olegtojgildin on 26/03/2019.
 */

public class FavoritesFragment extends Fragment implements FavoritesView, RecyclerItemTouchHelperListener {
    private FloatingActionButton mAddCurrencyFAB;
    private RecyclerView mRecyclerView;
    private FavoritesAdapter mAdapter;
    private List<CryptoCoinFullInfo> mFavoriteList;
    private List<String> mFavoriteListString;
    private FavoritesPresenter favoritesPresenter;
    private Spinner mSpinnerCategory;
    public String mCoin="USD";


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
        favoritesPresenter = new FavoritesPresenter(new FavoritesInteractorImpl(new FavoritesRepositoryImpl(new WebDataStoreImpl(),new DbDataStoreImpl(getContext()))));
        favoritesPresenter.attachView(this);
        initRecyclerView();
        initSpinnner();

        mAdapter.setOnClickListener(new FavoritesAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                FavoritesFragment.this.openDetailCurrency(mFavoriteList.get(position));
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
                mFavoriteListString.clear();
                mFavoriteList.clear();
                favoritesPresenter.loadCoin();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void openDetailCurrency(CryptoCoinFullInfo topCoin){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, CurrencyDetailFragment.newInstance1(topCoin.raw.crypto.cryptoCurrency.getFROMSYMBOL(),"USD"));
        transaction.addToBackStack(null);
        transaction.commit();
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

        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new FavoritesItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(mRecyclerView);

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
        favoritesPresenter.loadCoin();
    }

    public void initView(View view) {
        mAddCurrencyFAB = (FloatingActionButton) view.findViewById(R.id.fabAddCurrency);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_favorites);
        mSpinnerCategory = view.findViewById(R.id.coin_spinner_fav);

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
        favoritesPresenter.loadFavorites(mFavoriteListString, mCoin);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof FavoritesAdapter.ViewHolder) {
            String item = mFavoriteListString.get(viewHolder.getAdapterPosition());

            DbManager dbManager = DbManager.getInstance(getContext());
            dbManager.deleteNote(item);

            int deleteIndex = viewHolder.getAdapterPosition();
            mAdapter.remove(deleteIndex);

        }
    }
}
