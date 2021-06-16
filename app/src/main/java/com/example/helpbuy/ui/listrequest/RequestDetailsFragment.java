package com.example.helpbuy.ui.listrequest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.helpbuy.R;

import com.example.helpbuy.databinding.FragmentRequestdetailsBinding;

public class RequestDetailsFragment extends Fragment {

    private FragmentRequestdetailsBinding binding;

    public RequestDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requestdetails, container, false);
        // Inflate the layout for this fragment
        return view;
//            binding = FragmentRequestdetailsBinding.inflate(inflater, container, false);
//            View root = binding.getRoot();
//            return root;
    }

    public void onCreate(View view, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
}