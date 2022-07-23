//package com.ieeeias.vit_finders;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.bumptech.glide.Glide;
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthProvider;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//
//import java.net.MalformedURLException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.util.concurrent.TimeUnit;
//
//public class ItemDescriptionActivity extends AppCompatActivity {
//
//    private DatabaseReference mDatabaseReference;
//    private StorageReference mStorageReference;
//
//    private URL imageUrl;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
//        setContentView(R.layout.description);
//
//        mDatabaseReference = FirebaseDatabase.getInstance().getReference("items");
//        mStorageReference =  FirebaseStorage.getInstance().getReference();
//
//        TextView nameView = findViewById(R.id.nameView) ;
//        TextView brandView = findViewById(R.id.brandView);
//        TextView dateView = findViewById(R.id.dateView);
//        TextView contactView = findViewById(R.id.contactView);
//        TextView locView = findViewById(R.id.locView);
//        ImageView imageView = findViewById(R.id.imageView);
//        URI imageUri;
//
//        try {
//            imageUri = new URI(getIntent().getStringExtra("imageUrl"));
//            imageUrl = imageUri.toURL();
//        } catch (URISyntaxException | MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        Glide.with(getApplicationContext()).load(imageUrl).into(imageView);
//        nameView.setText(getIntent().getStringExtra("name"));
//        locView.setText(getIntent().getStringExtra("location"));
//        brandView.setText(getIntent().getStringExtra("brand"));
//        dateView.setText(getIntent().getStringExtra("date"));
//        contactView.setText(getIntent().getStringExtra("contact"));
//
//        Button button = findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////              send contact number from the database
//                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + "6393764373",
//                        60,
//                        TimeUnit.SECONDS,
//                        ItemDescriptionActivity.this,
//                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                            @Override
//                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                            }
//
//                            @Override
//                            public void onVerificationFailed(@NonNull FirebaseException e) {
//                                Toast.makeText(ItemDescriptionActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                            }
//
//                            @Override
//                            public void onCodeSent(@NonNull String backendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                super.onCodeSent(backendOtp, forceResendingToken);
//                                Intent intent = new Intent(getApplicationContext(), OtpVerifyActivity.class);
////                              send contact number from the database
//                                intent.putExtra("mobile", "6393764373");
//                                intent.putExtra("backendOtp", backendOtp);
//                                intent.putExtra("imageUrl", imageUrl);
//                                startActivity(intent);
//                            }
//                        });
//            }
//        });
//    }
//
//}

package com.ieeeias.vit_finders.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.ieeeias.vit_finders.PersonalInfo;
import com.ieeeias.vit_finders.R;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ItemDescriptionActivity extends AppCompatActivity {
    ImageView profile;
    ProgressBar pgbar;
    String img;
    private URL imageUrl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.description);

        TextView nameView = findViewById(R.id.nameView) ;
        TextView brandView = findViewById(R.id.brandView);
        TextView dateView = findViewById(R.id.dateView);
        TextView contactView = findViewById(R.id.contactView);
        TextView locView = findViewById(R.id.locView);
        ImageView imageView = findViewById(R.id.imageView);
        TextView categoryView = findViewById(R.id.categoryView);

        profile = findViewById(R.id.profile_image);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ItemDescriptionActivity.this, PersonalInfo.class);
                startActivity(intent);
            }
        });
        URI imageUri;

        try {
            imageUri = new URI(getIntent().getStringExtra("imageUrl"));
            imageUrl = imageUri.toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }

        Glide.with(getApplicationContext()).load(imageUrl).into(imageView);
//        img=getIntent().getStringExtra("imageUrl");
        Log.w("ItemDescription", "img = "+img);
        nameView.setText(getIntent().getStringExtra("name"));
        locView.setText(getIntent().getStringExtra("location"));
        brandView.setText(getIntent().getStringExtra("brand"));
        dateView.setText(getIntent().getStringExtra("date"));
        contactView.setText(getIntent().getStringExtra("contact"));
        categoryView.setText(getIntent().getStringExtra("category"));

        pgbar = findViewById(R.id.pg);
        pgbar.setVisibility(View.INVISIBLE);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(!ph.getText().toString().trim().isEmpty()){
//                    if(ph.getText().toString().trim().length()==10){
                pgbar.setVisibility(View.VISIBLE);
                button.setVisibility(View.INVISIBLE);
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("contact"),
                        60, TimeUnit.SECONDS, ItemDescriptionActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                pgbar.setVisibility(View.GONE);
//                                        otp.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
//                                        pgbar.setVisibility(View.GONE);
//                                        otp.setVisibility(View.VISIBLE);
                                Toast.makeText(ItemDescriptionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                pgbar.setVisibility(View.GONE);
                                button.setVisibility(View.VISIBLE);
//                                intent user id == prefManager getid
                                Intent intent= new Intent(getApplicationContext(), OtpVerifyActivity.class);
                                intent.putExtra("mobile", getIntent().getStringExtra("contact"));
                                intent.putExtra("backendOtp", s);
                                intent.putExtra("imageUrl", getIntent().getStringExtra("imageUrl"));
                                intent.putExtra("nameView", getIntent().getStringExtra("name"));
                                intent.putExtra("locView", getIntent().getStringExtra("location"));
                                intent.putExtra("dateView", getIntent().getStringExtra("date"));
                                intent.putExtra("brandView", getIntent().getStringExtra("brand"));
                                intent.putExtra("contactView", getIntent().getStringExtra("contact"));
                                intent.putExtra("category", getIntent().getStringExtra("category"));
                                intent.putExtra("userId", getIntent().getStringExtra("userId"));
                                startActivity(intent);
                            }
                        }
                );
            }
        });
    }

}