package com.example.helpbuy.ui.addoffer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.helpbuy.R;
import com.example.helpbuy.databinding.FragmentAddofferBinding;
import com.example.helpbuy.ui.addrequest.RequestDialogBox;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddOfferFragment extends Fragment {

    private AddOfferViewModel addOfferViewModel;
    private FragmentAddofferBinding binding;
    private Button btnSubmit;
    private EditText textLocation, textDateOfPurchase, textDuration, textMinFeesRequest, textRemarks;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addOfferViewModel =
                new ViewModelProvider(this).get(AddOfferViewModel.class);

        binding = FragmentAddofferBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnSubmit = (Button) root.findViewById(R.id.btn_submitoffer);
        textLocation = (EditText) root.findViewById(R.id.offerlocation);
        textDateOfPurchase = (EditText) root.findViewById(R.id.dateofpurchase);
        textDuration = (EditText) root.findViewById(R.id.duration);
        textMinFeesRequest = (EditText) root.findViewById(R.id.minfeesrequest);
        textRemarks = (EditText) root.findViewById(R.id.offerremarks);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String location = textLocation.getText().toString();
                String dateOfPurchase = textDateOfPurchase.getText().toString();
                String duration = textDuration.getText().toString();
                String minFeesRequest = textMinFeesRequest.getText().toString();
                String remarks = textRemarks.getText().toString();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                Map<String, String> newRequest = new HashMap<>();
                newRequest.put("Location", location);
                newRequest.put("Date of Purchase", dateOfPurchase);
                newRequest.put("Duration", duration);
                newRequest.put("Min. Fees Request", minFeesRequest);
                newRequest.put("Remarks", remarks);
                newRequest.put("UID", uid);
                newRequest.put("aUID","");

                db.collection("Job_offers")
                        .add(newRequest);


                OfferDialogBox dialogBox = new OfferDialogBox();
                dialogBox.show(getFragmentManager(),"dialog");

                textLocation.getText().clear();
                textDateOfPurchase.getText().clear();
                textMinFeesRequest.getText().clear();
                textDuration.getText().clear();
                textRemarks.getText().clear();

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