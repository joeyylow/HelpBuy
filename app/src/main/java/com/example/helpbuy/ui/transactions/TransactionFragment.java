package com.example.helpbuy.ui.transactions;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.helpbuy.R;
import com.example.helpbuy.ui.listoffer.OfferDetailsFragment;
import com.example.helpbuy.ui.listrequest.ListRequestFragment;
import com.google.firebase.firestore.DocumentSnapshot;

public class TransactionFragment extends Fragment {

    public static TransactionFragment newInstance() {
        return new TransactionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transactions, container, false);

        Button btnMyRequests = view.findViewById(R.id.myrequestsbutton);
        Button btnMyOffers = view.findViewById(R.id.myoffersbutton);
        Button btnMyAcceptedRequests = view.findViewById(R.id.myacceptedrequestsbutton);
        Button btnMyAcceptedOffers = view.findViewById(R.id.myacceptedoffersbutton);

        btnMyRequests.
                setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 ConstraintLayout fl = view.findViewById(R.id.transactionscontainer);
                                                 fl.removeAllViews();
                                                 MyRequestFragment detailsFragment = new MyRequestFragment();
                                                 getActivity()
                                                         .getSupportFragmentManager()
                                                         .beginTransaction()
                                                         .replace(R.id.transactionscontainer, detailsFragment)
                                                         .addToBackStack(null)
                                                         .commit();
                                             }
                                         });

        btnMyOffers.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyOfferFragment detailsFragment = new MyOfferFragment();
                        ConstraintLayout fl = view.findViewById(R.id.transactionscontainer);
                        fl.removeAllViews();
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.transactionscontainer, detailsFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });

        btnMyAcceptedRequests.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConstraintLayout fl = view.findViewById(R.id.transactionscontainer);
                        fl.removeAllViews();
                        AcceptedRequestFragment detailsFragment = new AcceptedRequestFragment();
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.transactionscontainer, detailsFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });

        btnMyAcceptedOffers.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConstraintLayout fl = view.findViewById(R.id.transactionscontainer);
                        fl.removeAllViews();
                        AcceptedOfferFragment detailsFragment = new AcceptedOfferFragment();
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.transactionscontainer, detailsFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    TransactionFragment fragment = new TransactionFragment();

                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.transactionscontainer, fragment)
                            .commit();
                    return true;
                }
                return false;
            }
        });
        return view;
    }

}

