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
import android.widget.ProgressBar;
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

public class ProfileFragment extends Fragment {
    Context context = null;
    PrefManager prefManager;
    DatabaseReference reference;
    GoogleSignInOptions gso;
    GoogleSignInClient googleSignInClient;
    TextView name, email, regNo, personName,id;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        prefManager = new PrefManager(this.getActivity());

        email = view.findViewById(R.id.email);
        personName = view.findViewById(R.id.personName);
        regNo = view.findViewById(R.id.regNo);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = mDatabaseReference.orderByChild("userId").equalTo(prefManager.getId());
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    progressBar.setVisibility(View.GONE);
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
//        acct = GoogleSignIn.getLastSignedInAccount(this.getActivity());
//
        Button signOut = view.findViewById(R.id.signout_button);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
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