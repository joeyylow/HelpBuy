package com.example.helpbuy.ui.listrequest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.helpbuy.MainActivity;
import com.example.helpbuy.NavigationActivity;
import com.example.helpbuy.R;


public class AcceptRequestDialogBox extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm submission")
                .setMessage("Job request accepted. Please contact the buyer for more information.")
                .setPositiveButton("Return", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ListRequestFragment fragment = new ListRequestFragment();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.requestlistfull, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                })
                .setNegativeButton("Contact buyer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        startActivity(new Intent(getActivity(), ChatActivity.class))
                    }
                });
        return builder.create();
    }
}