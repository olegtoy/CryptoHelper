package com.practice.olegtojgildin.crypto.presentation.view.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.practice.olegtojgildin.crypto.R;
import com.practice.olegtojgildin.crypto.data.api.RetrofitHelper;
import com.practice.olegtojgildin.crypto.data.models.CryptoHistoryPoint;
import com.practice.olegtojgildin.crypto.data.models.HistoryList;
import com.practice.olegtojgildin.crypto.data.models.topCurrency.CryptoCoinFullInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olegtojgildin on 12/04/2019.
 */

public class ChartFragment extends Fragment {
    private String fromSymbol;
    private String toSymbol;
    private TextView tvPrice;
    private TextView  tvHigh;
    private TextView  tvLow;
    private TextView  tvTime;
    private TextView tvName;
    private ImageView ivImage;
    private LineChart lineChart;
    private List<CryptoHistoryPoint> cryptoHistoryPoints;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initCoin();
        loadCurrency();
        loadHistory();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    public void loadCurrency(){
        new RetrofitHelper().getService()
                .getCoinsInfo(fromSymbol, toSymbol)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::putCoinInfo);
    }
    public void loadHistory(){
        new RetrofitHelper().getService()
                .getHistoryDay(fromSymbol, toSymbol,Integer.toString(30))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setHistoryList);
    }
    public void setHistoryList(HistoryList historyList){
        cryptoHistoryPoints=historyList.getItems();
        updateChart();
    }


    public void putCoinInfo(CryptoCoinFullInfo cryptoCoinFullInfo) {
        CryptoCoinFullInfo.Display.Crypto.Coin coin = cryptoCoinFullInfo.getDisplay().getCrypto().getCryptoCurrency();
        tvPrice.setText(coin.getPRICE());
        tvName.setText(fromSymbol);
        tvHigh.setText(coin.getHIGH24HOUR());
        tvLow.setText(coin.getLOW24HOUR());
        Picasso.get()
                .load(coin.getIMAGEURL())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(ivImage);
    }

    public void initView(View view){
        tvPrice = view.findViewById(R.id.tv_price);
        tvHigh  = view.findViewById(R.id.tv_high);
        tvHigh.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPositive));
        tvLow   = view.findViewById(R.id.tv_low);
        tvLow.setTextColor(ContextCompat.getColor(getContext(), R.color.colorNegative));
        tvTime  = view.findViewById(R.id.tv_time);
        tvName=view.findViewById(R.id.tv_name);
        ivImage=view.findViewById(R.id.iv_image);

        setupChart(view);
        setupListeners(view);
    }

    public void initCoin() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            toSymbol = bundle.getString("to");
            fromSymbol = bundle.getString("from");
        } else {
            toSymbol = "USD";
            fromSymbol = "BTC";
        }
    }

    private void setupChart(View view) {
        lineChart = view.findViewById(R.id.lineChart);
        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.setExtraBottomOffset(10.0f);
        lineChart.setExtraRightOffset(5.0f);
        lineChart.setDrawBorders(true);
        lineChart.setBorderWidth(0.5f);
        lineChart.setBorderColor(ContextCompat.getColor(getContext(), R.color.colorChartBorder));

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new ChartDateFormatter());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryText));
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);

        lineChart.getAxisLeft().setEnabled(false);

        YAxis leftRight = lineChart.getAxisRight();
        leftRight.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryText));
        leftRight.setDrawGridLines(true);
        leftRight.setDrawAxisLine(false);
    }


    private void setupListeners(View view){
        LineChart lineChart = view.findViewById(R.id.lineChart);
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                CryptoHistoryPoint cryptoHistoryPoint = (CryptoHistoryPoint) e.getData();
                selectedPointChanged(cryptoHistoryPoint);
            }

            @Override
            public void onNothingSelected() {
                selectLastPoint();
            }
        });
    }

    private void selectedPointChanged(CryptoHistoryPoint cryptoHistoryPoint) {
        String timeStr = DateFormatter.format(cryptoHistoryPoint.getTime());
        tvTime.setText(timeStr);

        String highStr = DecimalFormatter.formatFloat(cryptoHistoryPoint.getHigh());
        tvHigh.setText(highStr);

        String lowStr = DecimalFormatter.formatFloat(cryptoHistoryPoint.getLow());
        tvLow.setText(lowStr);

        String priceStr = DecimalFormatter.formatFloat(cryptoHistoryPoint.getClose()) + " " + toSymbol;
        tvPrice.setText(priceStr);
    }
    private void selectLastPoint(){
        if ((cryptoHistoryPoints != null) && (!cryptoHistoryPoints.isEmpty()) ) {
            selectedPointChanged(cryptoHistoryPoints.get(cryptoHistoryPoints.size() - 1));
        }
    }

    private void updateChart(){
        if ((cryptoHistoryPoints == null) || cryptoHistoryPoints.isEmpty()) return;

        List<Entry> entries = new ArrayList<Entry>();

        for (CryptoHistoryPoint cryptoHistoryPoint : cryptoHistoryPoints) {
            Entry entry = new Entry();

            entry.setX(cryptoHistoryPoint.getTime());
            entry.setY(cryptoHistoryPoint.getClose());
            entry.setData(cryptoHistoryPoint);

            entries.add(entry);
        }

        LineDataSet dataSet = new LineDataSet(entries, "");
        dataSet.setColor(ContextCompat.getColor(getContext(), R.color.colorChartMain));
        dataSet.setDrawCircles(false);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(ContextCompat.getColor(getContext(), R.color.colorChartMain));
        dataSet.setFillAlpha(200);
        dataSet.setDrawValues(false);
        dataSet.setHighLightColor(ContextCompat.getColor(getContext(), R.color.colorChartBorder));
        dataSet.setHighlightEnabled(true);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
        lineChart.setScaleEnabled(false);

        selectLastPoint();
    }


    public static ChartFragment newInstance(String fromSymbol, String toSymbol) {
        Bundle args = new Bundle();
        args.putString("to", toSymbol);
        args.putString("from", fromSymbol);
        ChartFragment fragment = new ChartFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
