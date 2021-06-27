package com.example.helpbuy.ui.addoffer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class OfferDialogBox extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        }).setTitle("Offer Submitted").setMessage("We have received your offer. " +
                "You can view it under Transactions > My Offers. " +
                "A buyer will contact you once he/ she accepts your offer.");
        return builder.create();
    }

}