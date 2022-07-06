package com.ieeeias.vit_finders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ItemDescriptionActivity extends AppCompatActivity {
    ImageView ig;

    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;

//    ListItem listItem = getIntent().getParcelableExtra("list item");

    private URL imageUrl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.description);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("items");
        mStorageReference =  FirebaseStorage.getInstance().getReference();

        TextView nameView = findViewById(R.id.nameView) ;
        TextView brandView = findViewById(R.id.brandView);
        TextView dateView = findViewById(R.id.dateView);
        TextView contactView = findViewById(R.id.contactView);
        TextView locView = findViewById(R.id.locView);
        ImageView imageView = findViewById(R.id.imageView);

        ig=findViewById(R.id.personal);
        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ItemDescriptionActivity.this,personalinfo.class);
                startActivity(intent);
            }
        });
        URI imageUri;
//        URL imageUrl;

        try {
            imageUri = new URI(getIntent().getStringExtra("imageUrl"));
            imageUrl = imageUri.toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }
//        Glide.with(getContext()).load(imageUrl).into(imageView);
        Glide.with(getApplicationContext()).load(imageUrl).into(imageView);
        nameView.setText(getIntent().getStringExtra("name"));
        locView.setText(getIntent().getStringExtra("location"));
        brandView.setText(getIntent().getStringExtra("brand"));
        dateView.setText(getIntent().getStringExtra("date"));
        contactView.setText(getIntent().getStringExtra("contact"));

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//              send contact number from the database
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + getIntent().getStringExtra("contact"),
                        60,
                        TimeUnit.SECONDS,
                        ItemDescriptionActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(ItemDescriptionActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String backendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                super.onCodeSent(backendOtp, forceResendingToken);
                                Intent intent = new Intent(getApplicationContext(), OtpVerifyActivity.class);
//                              send contact number from the database
                                intent.putExtra("mobile", getIntent().getStringExtra("contact"));
                                intent.putExtra("backendOtp", backendOtp);
                                startActivity(intent);
                            }
                        });
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(ItemDescriptionActivity.this,LostItemsActivity.class);
        startActivity(intent);
    }
}
