package com.ieeeias.vit_finders.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ieeeias.vit_finders.MainActivity;
import com.ieeeias.vit_finders.R;
import com.ieeeias.vit_finders.utils.PrefManager;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ProfileFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ProfileFragment extends Fragment {
    Context context = null;
    PrefManager prefManager;
    DatabaseReference reference;
    GoogleSignInOptions gso;
    GoogleSignInClient googleSignInClient;
    TextView name, email, regNo, personName,id;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ProfileFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ProfileFragment newInstance(String param1, String param2) {
//        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        prefManager = new PrefManager(this.getActivity());


//        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        personName = view.findViewById(R.id.personName);
        regNo = view.findViewById(R.id.regNo);
        //id = findViewById(R.id.id);

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = mDatabaseReference.orderByChild("userId").equalTo(prefManager.getId());
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    email.setText(snapshot.child(prefManager.getId()).child("email").getValue(String.class));
                    personName.setText(snapshot.child(prefManager.getId()).child("personName").getValue(String.class));
                    regNo.setText(snapshot.child(prefManager.getId()).child("regNo").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this.getActivity(), gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        acct = GoogleSignIn.getLastSignedInAccount(this.getActivity());
//        if (acct != null) {
//            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
//            String personEmail = acct.getEmail();
//            String personId = acct.getId();
//            prefManager.setId(personId);
//            Log.w(TAG, "user id = " + personId);
//            Uri personPhoto = acct.getPhotoUrl();
//            Log.w(TAG, "URI=" + personPhoto);
//            Log.w(TAG, "family name=" + personFamilyName);
//
//            name.setText(personName + " (Userid)");
//            //id.setText(personId+"id");
//            email.setText(personEmail + " (Emailid)");
//            regNo.setText(personFamilyName + " (Registration id)");
//            this.personName.setText(personGivenName + " (Name)");
//
//            //Glide.with(this).load(String.valueOf(personPhoto)).into(pic);
//            FirebaseFirestore db1 = FirebaseFirestore.getInstance();
//            // Create a new user with a first and last name
//
//            Map<String, Object> user = new HashMap<>();
//            user.put("Reference", personName);
//            // user.put("ID CODE", personId);
//            user.put("Names", personGivenName);
//            user.put("EMAIL", personEmail);
//            user.put("Details", personFamilyName);
//// Add a new document with a generated ID
//            db1.collection("Personal Info")
//                    .add(user)
//                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//
//                        public void onSuccess(DocumentReference documentReference) {
//                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w(TAG, "Error adding document", e);
//                        }
//                    });
//            db1.collection("Personal Info")
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    Log.d(TAG, document.getId() + " => " + document.getData());
//
//                                }
//                            } else {
//                                Log.w(TAG, "Error getting documents.", task.getException());
//                            }
//                        }
//                    });
//        }
//
        Button signOut = view.findViewById(R.id.signout_button);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
//                Toast.makeText(MainScreenActivity.this,"SignOut successful",Toast.LENGTH_LONG).show();
                Intent intent= new Intent(ProfileFragment.this.getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }
    private void signOut() {
        googleSignInClient = GoogleSignIn.getClient(this.getActivity(), gso);
        googleSignInClient.signOut()
                .addOnCompleteListener(this.getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ProfileFragment.this.getActivity(),"SignOut successful",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ProfileFragment.this.getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                });
    }
}