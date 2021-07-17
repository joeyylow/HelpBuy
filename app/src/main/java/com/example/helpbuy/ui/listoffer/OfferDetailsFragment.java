package com.example.helpbuy.ui.listoffer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.helpbuy.R;

import com.example.helpbuy.databinding.FragmentOfferdetailsBinding;
import com.example.helpbuy.ui.listrequest.AcceptRequestDialogBox;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Map;

public class OfferDetailsFragment extends Fragment{
    //private FragmentOfferdetailsBinding binding;
    private String locationString;
    private String dateString;
    private String timeString;
    private String deliveryfeesString;
    private String remarksString;
    private String documentID;
    private String UID;

    public OfferDetailsFragment() {
        // Required empty public constructor
    }

    public OfferDetailsFragment(String documentID, String locationString, String dateString, String timeString,
                                  String deliveryfeesString,  String remarksString, String UIDString){
        this.documentID = documentID;
        this.locationString = locationString;
        this.dateString = dateString;
        this.timeString = timeString;
        this.deliveryfeesString = deliveryfeesString;
        this.remarksString = remarksString;
        this.UID = UIDString;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offerdetails, container, false);
        TextView location = view.findViewById(R.id.offerdetails_location);
        TextView date = view.findViewById(R.id.offerdetails_deliverydate);
        TextView time = view.findViewById(R.id.offerdetails_deliverytime);
        TextView deliveryfees = view.findViewById(R.id.offerdetails_deliveryfees);
        TextView remarks = view.findViewById(R.id.offerdetails_remarks);
        TextView user = view.findViewById(R.id.delivererusername);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users").document(this.UID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    String username = (String) document.get("Username");

                    location.setText(locationString);
                    date.setText(dateString);
                    time.setText(dateString);
                    deliveryfees.setText(deliveryfeesString);
                    remarks.setText(remarksString);
                    user.setText(username);
                }
            }
        });


        //Button activity
        Button btnAcceptOffer = (Button) view.findViewById(R.id.acceptofferbutton);

        btnAcceptOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Job_offers").document(documentID).update("aUID",FirebaseAuth.getInstance().getCurrentUser().getUid());
                AcceptOfferDialogBox acceptOfferDialogBox = new AcceptOfferDialogBox();
                acceptOfferDialogBox.show(getFragmentManager(),"acceptofferdialog");
//                DocumentReference docRef = db.collection("Job_offers").document(documentID);
//                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            Map<String, Object> newAcceptedOffer = document.getData();
//                            newAcceptedOffer.put("aUID", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                            db.collection("Accepted_job_offers").add(newAcceptedOffer);
//                            db.collection("Job_offers").document(documentID).delete();
//                            openDialog();
//                        }
//                    }
//                    public void openDialog() {
//                        AcceptOfferDialogBox acceptOfferDialogBox = new AcceptOfferDialogBox();
//                        acceptOfferDialogBox.show(getFragmentManager(),"acceptofferdialog");
//                    }
//                });
            }
        });



//        btnChat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), MainActivity.class));
//            }
//        });


        return view;
    }

    public void onCreate(View view, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
}

