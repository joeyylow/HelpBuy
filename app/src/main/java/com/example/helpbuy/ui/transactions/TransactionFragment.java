package com.example.helpbuy.ui.transactions;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.helpbuy.R;
import com.example.helpbuy.databinding.FragmentAddrequestBinding;
import com.example.helpbuy.ui.settings.SettingsFragment;
import com.example.helpbuy.ui.settings.SettingsViewModel;

public class TransactionFragment extends Fragment {

    public static TransactionFragment newInstance() {
        return new TransactionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transactions, container, false);

        Button btnViewTransactions = view.findViewById(R.id.viewtransactionsbutton);
        btnViewTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),TransactionActivity.class));
            }
        });

        return view;
    }

}

