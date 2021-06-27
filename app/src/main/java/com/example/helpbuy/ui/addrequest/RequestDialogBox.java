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
        }).setTitle("Request Submitted").setMessage("We have received your request. " +
                "You can view it under Transactions > My Requests. " +
                "A deliverer will contact you once he/ she accepts your request.");
        return builder.create();
    }

}