package com.ieeeias.vit_finders;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class personalinfo extends AppCompatActivity {
    String dsame=null;
    DatabaseReference reference;

    GoogleSignInClient googleSignInClient;
    TextView name, email, personfamily, persongiven,id;

    ImageView pic;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.personinfo);


       name=findViewById(R.id.namee);
        email = findViewById(R.id.pegv);
        persongiven = findViewById(R.id.persongivenname);
        personfamily = findViewById(R.id.personfamily);
        //id = findViewById(R.id.id);
        pic = findViewById(R.id.personal);





        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        //acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
             String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            //String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            name.setText(personName+" (Userid)");
            //id.setText(personId+"id");
            email.setText(personEmail+" (Emailid)");
            personfamily.setText(personFamilyName+" (Registration id)");
            persongiven.setText(personGivenName+" (Name)");

            Glide.with(this).load(String.valueOf(personPhoto)).into(pic);
            FirebaseFirestore db1 = FirebaseFirestore.getInstance();
            // Create a new user with a first and last name

            Map<String, Object> user = new HashMap<>();
            user.put("Reference",  personName);
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

//        signout.setOnClickListener(view -> {
//
//
//                AlertDialog.Builder build= new AlertDialog.Builder(this);
//                build.setMessage("ARE YOU SURE YOU WANT TO CONFIRM YOUR DETAILS")
//                        .setCancelable(false)
//                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                finish();
//                                Intent intent= new Intent(personalinfo.this,AddItemActivity.class);
//                                startActivity(intent);
//                            }
//                        })
//                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.cancel();
//
//                            }
//                        });
//                AlertDialog alert =build.create();
//                alert.show();
//
//
//        });

//        FirebaseUser user1= FirebaseAuth.getInstance().getCurrentUser();
//        {
//            if(user1!=null){
//                Log.d(TAG,"created"+ user1.getDisplayName());
//                if(user1.getDisplayName()!=null){
//                    name.setText(user1.getDisplayName());
//
//
//                }

            }

        }
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//               // update();
//                Toast.makeText(personalinfo.this,"Done",Toast.LENGTH_LONG).show();
//
//            }
//        });









//    private void update() {
//        dsame=name.toString();
//        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
//        UserProfileChangeRequest userProfileChangeRequest= new UserProfileChangeRequest.Builder()
//                .setDisplayName(dsame)
//                .build();
//        firebaseUser.updateProfile(userProfileChangeRequest)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(personalinfo.this,"Done",Toast.LENGTH_LONG).show();
//
//                    }
//                });

    //}








