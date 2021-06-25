package com.example.helpbuy.ui.addrequest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.helpbuy.R;
import com.example.helpbuy.ui.listoffer.ListOfferFragment;

public class RequestDialogBox extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        }).setTitle("Request Submitted").setMessage("A deliverer will contact you when the request is accepted. " +
                "You can also view your request under Transactions");
        return builder.create();
    }

}