package com.example.helpbuy.ui.addrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.helpbuy.databinding.FragmentAddrequestBinding;

public class AddRequestFragment extends Fragment {

    private AddRequestViewModel addRequestViewModel;
    private FragmentAddrequestBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addRequestViewModel =
                new ViewModelProvider(this).get(AddRequestViewModel.class);

        binding = FragmentAddrequestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAddrequest;
        addRequestViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}