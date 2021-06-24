package com.example.helpbuy.ui.transactions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.helpbuy.NavigationActivity;
import com.example.helpbuy.R;
import com.example.helpbuy.ui.listoffer.ListOfferFragment;
import com.example.helpbuy.ui.listrequest.ListRequestFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TransactionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);

        TransactionHistoryAdapter adapter = new TransactionHistoryAdapter(this);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0: {
                                tab.setText("My Requests");
                                break;
                            }
                            case 1: {
                                tab.setText("My Accepted Requests");
                                break;
                            }
                            case 2:{
                                tab.setText("My Offers");
                                break;
                            }
                            case 3:{
                                tab.setText("My Accepted Offers");
                                break;
                            }
                        }
                    }
                }).attach();
    }
}