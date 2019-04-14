package com.practice.olegtojgildin.crypto.presentation.view.personalFinance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.practice.olegtojgildin.crypto.R;
import com.practice.olegtojgildin.crypto.presentation.view.selectCurrency.SelectCurrencyActivity;

/**
 * Created by olegtojgildin on 12/04/2019.
 */

public class AddCoinWithCountActivity extends AppCompatActivity {
    private Spinner mSpinnerCategory;
    private String nameCoin;
    private EditText countCoin;
    private Button mAddBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coint_count);

        initView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.crypto1, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCategory.setAdapter(adapter);
        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nameCoin = (String) adapterView.getItemAtPosition(i);
                // updateActivity();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        initListener();


    }

    public void initView() {
        countCoin = findViewById(R.id.countAdd);
        mSpinnerCategory = findViewById(R.id.wallet_add_coin);
        mAddBtn = findViewById(R.id.buttonAddtoWallet);

    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddCoinWithCountActivity.class);
    }

    public void initListener() {
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent();
                    intent.putExtra("name", nameCoin);
                    if (countCoin.getText() != null)
                        intent.putExtra("count", Double.parseDouble(countCoin.getText().toString()));
                    else
                        intent.putExtra("count", 0.0);
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (NumberFormatException ex) {
                    Log.d("error", ex.getMessage().toString());
                }
            }
        });
    }
}
