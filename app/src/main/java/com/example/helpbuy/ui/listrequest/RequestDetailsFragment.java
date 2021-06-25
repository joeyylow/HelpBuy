package com.example.helpbuy.ui.listrequest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.helpbuy.R;

import com.example.helpbuy.databinding.FragmentRequestdetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Map;

public class RequestDetailsFragment extends Fragment {

    private FragmentRequestdetailsBinding binding;
    private String itemString;
    private String locationString;
    private String dateString;
    private String timeString;
    private String deliveryfeesString;
    private String quantityString;
    private String remarksString;
    private String documentID;
    private String UID;

    public RequestDetailsFragment() {
        // Required empty public constructor
    }

    public RequestDetailsFragment(String documentID, String itemString, String locationString, String dateString, String timeString,
                                  String deliveryfeesString, String quantityString,  String remarksString, String UIDString){
        this.documentID = documentID;
        this.itemString = itemString;
        this.locationString = locationString;
        this.dateString = dateString;
        this.timeString = timeString;
        this.deliveryfeesString = deliveryfeesString;
        this.quantityString = quantityString;
        this.remarksString = remarksString;
        this.UID = UIDString;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Set text for details
        View view = inflater.inflate(R.layout.fragment_requestdetails, container, false);
        TextView item = view.findViewById(R.id.requestdetails_item);
        TextView location = view.findViewById(R.id.requestdetails_location);
        TextView date = view.findViewById(R.id.requestdetails_deliverydate);
        TextView time = view.findViewById(R.id.requestdetails_deliverytime);
        TextView deliveryfees = view.findViewById(R.id.requestdetails_deliveryfees);
        TextView remarks = view.findViewById(R.id.requestdetails_remarks);
        TextView quantity = view.findViewById(R.id.requestdetails_quantity);
        TextView user = view.findViewById(R.id.buyerusername);

//        item.setText(itemString);
//        location.setText(locationString);
//        date.setText(dateString);
//        time.setText(dateString);
//        deliveryfees.setText(deliveryfeesString);
//        quantity.setText(quantityString);
//        remarks.setText(remarksString);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users").document(this.UID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    String username = (String) document.get("Username");
                    user.setText(username);

                    item.setText(itemString);
                    location.setText(locationString);
                    date.setText(dateString);
                    time.setText(dateString);
                    deliveryfees.setText(deliveryfeesString);
                    quantity.setText(quantityString);
                    remarks.setText(remarksString);
                }
            }
        });


        //Button activity
        Button btnAcceptReq = (Button) view.findViewById(R.id.acceptreqbutton);


        btnAcceptReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Job_requests").document(documentID).update("aUID",FirebaseAuth.getInstance().getCurrentUser().getUid());
                AcceptRequestDialogBox acceptRequestDialogBox = new AcceptRequestDialogBox();
                acceptRequestDialogBox.show(getFragmentManager(),"acceptrequestdialog");
//                DocumentReference docRef = db.collection("Job_requests").document(documentID);
//                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
////                            DocumentSnapshot document = task.getResult();
////                            Map<String, Object> newAcceptedRequest = document.getData();
////                            newAcceptedRequest.put("aUID", FirebaseAuth.getInstance().getCurrentUser().getUid());
////                            db.collection("Accepted_job_requests").add(newAcceptedRequest);
////                            db.collection("Job_requests").document(documentID).delete();
//                            openDialog();
//                        }
//                    }

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