package com.ieeeias.vit_finders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otp1 extends AppCompatActivity {
    TextView ph;
    Button otp;
    ProgressBar pgbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.otpp);
        ph=findViewById(R.id.ph);
        otp=findViewById(R.id.otp);
        pgbar=findViewById(R.id.pg);
        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ph.getText().toString().trim().isEmpty()){
                    if(ph.getText().toString().trim().length()==10){
                           pgbar.setVisibility(View.VISIBLE);
                           otp.setVisibility(View.INVISIBLE);
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + ph.getText().toString(), 60, TimeUnit.SECONDS, otp1.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        pgbar.setVisibility(View.GONE);
                                        otp.setVisibility(View.VISIBLE);


                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        pgbar.setVisibility(View.GONE);
                                        otp.setVisibility(View.VISIBLE);
                                        Toast.makeText(otp1.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        super.onCodeSent(s, forceResendingToken);
                                        pgbar.setVisibility(View.GONE);
                                        otp.setVisibility(View.VISIBLE);
                                        Intent intent= new Intent(getApplicationContext(),otp2.class);
                                        intent.putExtra("Mobile",ph.getText().toString());
                                        intent.putExtra("backendotp",s);
                                        startActivity(intent);

                                    }
                                }
                        );

//                           Intent intent= new Intent(getApplicationContext(),otp2.class);
////                        intent.putExtra("Mobile",ph.getText().toString());
////                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(otp1.this, "Please Enter correct mobile number", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(otp1.this,"Enter mobilenumber",Toast.LENGTH_LONG).show();
                }

            }
        });
}

}
