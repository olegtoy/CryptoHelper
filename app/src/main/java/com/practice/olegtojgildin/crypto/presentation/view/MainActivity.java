package com.practice.olegtojgildin.crypto.presentation.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.practice.olegtojgildin.crypto.R;
import com.practice.olegtojgildin.crypto.presentation.view.convert.ConvertFragment;
import com.practice.olegtojgildin.crypto.presentation.view.favorites.FavoritesFragment;
import com.practice.olegtojgildin.crypto.presentation.view.news.NewsFragment;
import com.practice.olegtojgildin.crypto.presentation.view.personalFinance.WalletFragment;
import com.practice.olegtojgildin.crypto.presentation.view.topCurrency.CurrencyFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView mBottomNavigation = findViewById(R.id.bottom_navigation);
        mBottomNavigation.setOnNavigationItemSelectedListener(mNavListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CurrencyFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mNavListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_news:
                            selectedFragment = new NewsFragment();
                            break;
                        case R.id.nav_favo:
                            selectedFragment = new FavoritesFragment();
                            break;
                        case R.id.nav_currency:
                            selectedFragment = new CurrencyFragment();
                            break;
                        case R.id.nav_convert:
                            selectedFragment = new ConvertFragment();
                            break;
                        case R.id.nav_wallet:
                            selectedFragment = new WalletFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}
