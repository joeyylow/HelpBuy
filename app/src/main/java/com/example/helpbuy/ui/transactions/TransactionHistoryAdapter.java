package com.example.helpbuy.ui.transactions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.helpbuy.ui.listoffer.ListOfferFragment;
import com.example.helpbuy.ui.listrequest.ListRequestFragment;

public class TransactionHistoryAdapter extends FragmentStateAdapter {

    public TransactionHistoryAdapter(@NonNull FragmentActivity fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return  new MyRequestFragment();
            case 1:
                return  new AcceptedRequestFragment();
            case 2:
                return new MyOfferFragment();
            default:
                return new AcceptedOfferFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
