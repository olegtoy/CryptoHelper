package com.practice.olegtojgildin.crypto.presentation.view.news;

import android.content.Intent;
import android.net.Uri;
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
import com.practice.olegtojgildin.crypto.data.repositories.NewsRepositoryImpl;
import com.practice.olegtojgildin.crypto.data.datastore.WebDataStoreImpl;
import com.practice.olegtojgildin.crypto.domain.news.NewsInteractorImpl;
import com.practice.olegtojgildin.crypto.data.models.news.CryptoNewsArticle;
import com.practice.olegtojgildin.crypto.data.models.news.NewsList;
import com.practice.olegtojgildin.crypto.presentation.presenter.NewsPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olegtojgildin on 26/03/2019.
 */

public class NewsFragment extends Fragment implements NewsView {

    private List<CryptoNewsArticle> mNewsList;
    private RecyclerView mRecyclerView;
    private NewsListAdapter mNewsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Spinner mSpinnerCategory;
    private String categoryNews="BTC";


    private NewsPresenter newsPresenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initRecyclerView();
       // loadNewsList();
        initListener();
        initSpinnner();
    }

    public void initView(View view) {

        mRecyclerView = view.findViewById(R.id.rv_news);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        mSpinnerCategory = view.findViewById(R.id.spinner_select_cat);

        newsPresenter=new NewsPresenter(new NewsInteractorImpl(new NewsRepositoryImpl(new WebDataStoreImpl())));
        newsPresenter.attachView(this);
        newsPresenter.loadNewsList(categoryNews);
    }

    public void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateActivity();
            }
        });
        mNewsAdapter.setOnClickListener(new NewsListAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                CryptoNewsArticle cryptoNewsArticle = mNewsAdapter.getNewsItem(position);
               // openWebSite(cryptoNewsArticle.getUrl());
                openDetailNews(cryptoNewsArticle);
            }
        });
    }

    public void initSpinnner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCategory.setAdapter(adapter);
        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item=(String)adapterView.getItemAtPosition(i);
                categoryNews=item;
                updateActivity();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setAutoMeasureEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);

        mNewsList = new ArrayList<>();
        mNewsAdapter = new NewsListAdapter(getContext(), mNewsList);
        mRecyclerView.setAdapter(mNewsAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

   /* public void loadNewsList() {
        if(categoryNews.equals("Latest News")) {
            new RetrofitHelper().getService()
                    .getLatestNews("EN")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<NewsList>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(NewsList newsList) {
                            mNewsList = newsList.getItems();
                            mNewsAdapter.setListNews(mNewsList);
                            mNewsAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            mSwipeRefreshLayout.setRefreshing(false);

                        }
                    });
        }
        else {
            new RetrofitHelper().getService()
                    .getNews(categoryNews, "EN")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<NewsList>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(NewsList newsList) {
                            mNewsList = newsList.getItems();
                            mNewsAdapter.setListNews(mNewsList);
                            mNewsAdapter.notifyDataSetChanged();
                            Log.d("size", Integer.toString(mNewsList.size()));
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            mSwipeRefreshLayout.setRefreshing(false);

                        }
                    });
        }
    }*/

    private void openWebSite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void openDetailNews(CryptoNewsArticle cryptoNewsArticle){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, NewsDetailFragment.newInstance(cryptoNewsArticle));
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void updateActivity() {
        mNewsList.clear();
        newsPresenter.loadNewsList(categoryNews);
        mNewsAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void setData(NewsList newsList) {
        mNewsAdapter.setListNews(newsList.getItems());
        mNewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }
}
