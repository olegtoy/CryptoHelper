package com.practice.olegtojgildin.crypto.presentation.view.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.practice.olegtojgildin.crypto.R;
import com.practice.olegtojgildin.crypto.data.models.news.CryptoNewsArticle;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by olegtojgildin on 16/03/2019.
 */


public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private OnClickListener mOnClickListener;
    private List<CryptoNewsArticle> mNewsList;
    private Context mContext;

    public NewsListAdapter(Context context, List<CryptoNewsArticle> newsItems) {
        this.mNewsList = newsItems;
        this.mContext = context;
    }

    public void setOnClickListener(OnClickListener onListener) {
        this.mOnClickListener = onListener;
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public void setListNews(List<CryptoNewsArticle> list) {
        mNewsList = list;
    }

    public CryptoNewsArticle getNewsItem(int position) {
        return mNewsList.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.row_news, parent, false);
        return new ViewHolder(view, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        CryptoNewsArticle cryptoNewsArticle = getNewsItem(position);

        viewHolder.tvTitle.setText(cryptoNewsArticle.getTitle());
        viewHolder.tvSource.setText(cryptoNewsArticle.getSource());
        viewHolder.tvDate.setText(getRelativeDateString(cryptoNewsArticle.getPublished_on()));

        Picasso.get()
                .load(cryptoNewsArticle.getImageurl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(viewHolder.mImageView);
    }



    private String getRelativeDateString(long dateValue) {
        long date = dateValue * 1000L;
        long now = System.currentTimeMillis();
        Long difference = now - date;

        if (TimeUnit.MILLISECONDS.toMinutes(difference) <= 1) {
            return mContext.getString(R.string.common_just_now);
        } else if (TimeUnit.MILLISECONDS.toMinutes(difference) < 60) {
            return (String) DateUtils.getRelativeTimeSpanString(date, now,
                    DateUtils.MINUTE_IN_MILLIS);
        }
        return (String) DateUtils.getRelativeTimeSpanString(date, now, DateUtils.HOUR_IN_MILLIS);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvSource;
        TextView tvDate;
        ImageView mImageView;
        OnClickListener ocListener;

        public ViewHolder(View view, OnClickListener ocListener) {
            super(view);

            tvTitle = view.findViewById(R.id.tv_title);
            tvSource = view.findViewById(R.id.tv_source);
            tvDate = view.findViewById(R.id.tv_date);
            mImageView=itemView.findViewById(R.id.image_news);

            this.ocListener = ocListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (ocListener != null) {
                ocListener.onClick(view, getAdapterPosition());
            }
        }
    }

    public interface OnClickListener {
        void onClick(View view, int position);
    }

}
