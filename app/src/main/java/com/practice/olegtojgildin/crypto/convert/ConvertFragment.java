package com.practice.olegtojgildin.crypto.convert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.practice.olegtojgildin.crypto.R;
import com.practice.olegtojgildin.crypto.data.api.RetrofitHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by olegtojgildin on 26/03/2019.
 */

public class ConvertFragment extends Fragment {
    private EditText mFromCount;
    private EditText mToCount;
    private Spinner mSpinnerFrom;
    private Spinner mSpinnerTo;
    private String mFrom = "BTC";
    private String mTo = "USD";
    private Double mFromDouble = 0.0;
    private Double mToDouble = 0.0;
    private Double mPrice = 0.0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_convert, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initSpinnner();
    }

    public void initView(View view) {
        mFromCount = view.findViewById(R.id.from_count);
        mToCount = view.findViewById(R.id.to_count);
        mSpinnerFrom = view.findViewById(R.id.spinner_from_convert);
        mSpinnerTo = view.findViewById(R.id.spinner_to_convert);
    }


    public void initSpinnner() {
        ArrayAdapter<CharSequence> adapterTo = ArrayAdapter.createFromResource(getContext(), R.array.crypto, android.R.layout.simple_spinner_item);
        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTo.setAdapter(adapterTo);
        mSpinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mTo = (String) adapterView.getItemAtPosition(i);
                loadCurrency(mFrom, mTo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapterFrom = ArrayAdapter.createFromResource(getContext(), R.array.crypto, android.R.layout.simple_spinner_item);
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerFrom.setAdapter(adapterFrom);
        mSpinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mFrom = (String) adapterView.getItemAtPosition(i);
                loadCurrency(mFrom, mTo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mFromCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (!editable.toString().equals(""))
                        mFromDouble = Double.parseDouble(editable.toString());
                } catch (NumberFormatException ex) {
                    Log.d("Exeption", ex.getMessage());
                }
                loadCurrency(mFrom, mTo);
            }
        });
    }

    public void putCoinInfos(SinglePrice price) {
        mPrice = Double.parseDouble(price.getPrices());
        mToCount.setText(Double.toString(mPrice * mFromDouble));
    }

    public void loadCurrency(String from, String to) {
        new RetrofitHelper().getService()
                .getSinglePrice(from, to)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::putCoinInfos);
    }
}
