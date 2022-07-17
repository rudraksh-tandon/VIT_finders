package com.ieeeias.vit_finders;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link BlankFragment5#newInstance} factory method to
// * create an instance of this fragment.
// */
public class BlankFragment5 extends Fragment {
    Context context = null;
    PrefManager prefManager;
    DatabaseReference reference;

    GoogleSignInClient googleSignInClient;
    TextView name, email, personfamily, persongiven,id;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragment5() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment BlankFragment5.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static BlankFragment5 newInstance(String param1, String param2) {
//        BlankFragment5 fragment = new BlankFragment5();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank5, container, false);
        prefManager = new PrefManager(this.getActivity());


        name = view.findViewById(R.id.namee);
        email = view.findViewById(R.id.pegv);
        persongiven = view.findViewById(R.id.persongivenname);
        personfamily = view.findViewById(R.id.personfamily);
        //id = findViewById(R.id.id);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this.getActivity(), gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        //acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            prefManager.setId(personId);
            Log.w(TAG, "user id = " + personId);
            Uri personPhoto = acct.getPhotoUrl();
            Log.w(TAG, "URI=" + personPhoto);
            Log.w(TAG, "family name=" + personFamilyName);

            name.setText(personName + " (Userid)");
            //id.setText(personId+"id");
            email.setText(personEmail + " (Emailid)");
            personfamily.setText(personFamilyName + " (Registration id)");
            persongiven.setText(personGivenName + " (Name)");

            //Glide.with(this).load(String.valueOf(personPhoto)).into(pic);
            FirebaseFirestore db1 = FirebaseFirestore.getInstance();
            // Create a new user with a first and last name

            Map<String, Object> user = new HashMap<>();
            user.put("Reference", personName);
            // user.put("ID CODE", personId);
            user.put("Names", personGivenName);
            user.put("EMAIL", personEmail);
            user.put("Details", personFamilyName);
// Add a new document with a generated ID
            db1.collection("Personal Info")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
            db1.collection("Personal Info")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());

                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
            return view;
    }
}