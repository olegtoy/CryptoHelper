package com.practice.olegtojgildin.crypto.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.olegtojgildin.crypto.R;
import com.squareup.picasso.Picasso;

/**
 * Created by olegtojgildin on 28/03/2019.
 */

public class NewsDetailFragment extends Fragment {
    private TextView mTitle;
    private TextView mBody;
    private TextView mSource;
    private ImageView mImageNews;
    CryptoNewsArticle mNewsArticle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initArticle(view);
    }

    public void initArticle(View view) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mNewsArticle = bundle.getParcelable("Article");
        }
        mBody.setText(mNewsArticle.getBody());
        mSource.setText(mNewsArticle.getSource());


        SpannableString spannableString = new SpannableString(mNewsArticle.getTitle());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mNewsArticle.getUrl())));
            }
        };

        spannableString.setSpan(clickableSpan, 0,
                spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTitle.setText(spannableString, TextView.BufferType.SPANNABLE);
        mTitle.setMovementMethod(LinkMovementMethod.getInstance());

        Picasso.get()
                .load(mNewsArticle.getImageurl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(mImageNews);
    }

    public void initView(View view) {
        mTitle = view.findViewById(R.id.title_news);
        mBody = view.findViewById(R.id.body_news);
        mSource = view.findViewById(R.id.source_news);
        mImageNews=view.findViewById(R.id.image_news_detail);
    }

    public static NewsDetailFragment newInstance(CryptoNewsArticle cryptoNewsArticle) {
        Bundle args = new Bundle();
        args.putParcelable("Article", cryptoNewsArticle);
        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
