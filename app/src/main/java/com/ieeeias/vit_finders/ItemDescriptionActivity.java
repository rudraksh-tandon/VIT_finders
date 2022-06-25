package com.ieeeias.vit_finders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.TimeUnit;

public class ItemDescriptionActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.description);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("items");
        mStorageReference =  FirebaseStorage.getInstance().getReference();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              send contact number from the database
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + "6393764373",
                        60,
                        TimeUnit.SECONDS,
                        ItemDescriptionActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                        delete the item from database and then move to the lost items list
//                        Toast.makeText(ItemDescriptionActivity.this, "OTP verified successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ItemDescriptionActivity.this, LostItemsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(ItemDescriptionActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                            @Override
                            public void onCodeSent(@NonNull String backendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                super.onCodeSent(s, forceResendingToken);
                                Intent intent = new Intent(ItemDescriptionActivity.this, OtpVerifyActivity.class);
//                              send contact number from the database
                                intent.putExtra("mobile", "6393764373");
                                intent.putExtra("backendOtp", backendOtp);
                                startActivity(intent);
                            }
                        });
            }
        });

        
    }
}
