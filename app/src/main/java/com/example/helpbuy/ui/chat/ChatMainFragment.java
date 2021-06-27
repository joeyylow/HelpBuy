package com.example.helpbuy.ui.chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.helpbuy.R;
import com.example.helpbuy.ui.transactions.TransactionFragment;

public class ChatMainFragment extends Fragment {

    public static ChatMainFragment newInstance() {
        return new ChatMainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_main, container, false);

        Button btnViewChats = view.findViewById(R.id.beginChatButton);
        btnViewChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChatMainActivity.class));
            }
        });
        return view;
    }
}