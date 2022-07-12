package com.ieeeias.vit_finders;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class OtpVerifyActivity extends AppCompatActivity {
    EditText inputNum1, inputNum2, inputNum3, inputNum4, inputNum5, inputNum6;
    TextView sentText, resendText;
    Button button;
    String backendOtp;
    ProgressBar pgbar, pgbar2;
//    String img;
//    ItemDescriptionActivity ida=new ItemDescriptionActivity();
//    String imgf=ida.img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.otp_verify);
        //tt=findViewById(R.id.tt);
        inputNum1 = findViewById(R.id.inputNum1);
        inputNum2 = findViewById(R.id.inputNum2);
        inputNum3 = findViewById(R.id.inputNum3);
        inputNum4 = findViewById(R.id.inputNum4);
        inputNum5 = findViewById(R.id.inputNum5);
        inputNum6 = findViewById(R.id.inputNum6);

        sentText = findViewById(R.id.sentText);
        sentText.setText("OTP sent to " + getIntent().getStringExtra("mobile"));
        backendOtp = getIntent().getStringExtra("backendOtp");

        //tt.setText(String.format("+91-%s",getIntent().getStringExtra("Mobile")));

//        Log.w(, "img="+imgf);
//        System.out.print("imgf = " + imgf);
        pgbar2 = findViewById(R.id.pg2);
        pgbar2.setVisibility(View.INVISIBLE);

        resendText = findViewById(R.id.resendText);
        resendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pgbar2.setVisibility(View.VISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        OtpVerifyActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                pgbar2.setVisibility(View.GONE);
//                        delete the item from database and then move to the lost items list
//                                Toast.makeText(OtpVerifyActivity.this, "OTP verified successfully", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(ItemDescriptionActivity.this, LostItemsActivity.class);
//                        startActivity(intent);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                pgbar2.setVisibility(View.GONE);
                                Toast.makeText(OtpVerifyActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newBackendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                pgbar2.setVisibility(View.GONE);
                                backendOtp = newBackendOtp;
                                Toast.makeText(OtpVerifyActivity.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        pgbar = findViewById(R.id.pg);
        pgbar.setVisibility(View.INVISIBLE);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                pgbar.setVisibility(View.VISIBLE);
                button.setVisibility(View.INVISIBLE);

                if(!inputNum1.toString().isEmpty() && !inputNum2.toString().isEmpty() &&
                        !inputNum3.toString().isEmpty() && !inputNum4.toString().isEmpty() &&
                        !inputNum5.toString().isEmpty() && !inputNum6.toString().isEmpty()){

                    String inputOtp = inputNum1.getText().toString() +
                            inputNum2.getText().toString() +
                            inputNum3.getText().toString() +
                            inputNum4.getText().toString() +
                            inputNum5.getText().toString() +
                            inputNum6.getText().toString();
                    if(backendOtp!=null){
                        PhoneAuthCredential pho= PhoneAuthProvider.getCredential(backendOtp,inputOtp);
                        FirebaseAuth.getInstance().signInWithCredential(pho).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    pgbar.setVisibility(View.GONE);
                                    button.setVisibility(View.VISIBLE);

                                    onDelete(getIntent().getStringExtra("imageUrl"));
                                    Toast.makeText(OtpVerifyActivity.this, "OTP verified successfully", Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(OtpVerifyActivity.this,MainScreenActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                                else {
                                    pgbar.setVisibility(View.GONE);
                                    button.setVisibility(View.VISIBLE);
                                    Toast.makeText(OtpVerifyActivity.this, "Please Enter correct OTP", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                    else {
                        pgbar.setVisibility(View.GONE);
                        button.setVisibility(View.VISIBLE);
                        Toast.makeText(OtpVerifyActivity.this, "Please check your Internet connection", Toast.LENGTH_SHORT).show();

                    }
                    //Toast.makeText(OtpVerifyActivity.this, "OTP VERIFY", Toast.LENGTH_SHORT).show();

                }else {
                    pgbar.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                    Toast.makeText(OtpVerifyActivity.this, "Please Enter correct OTP", Toast.LENGTH_SHORT).show();

                }
            }
        });
        numberOtpMove();

    }

    private void numberOtpMove(){
        inputNum1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputNum2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        inputNum2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputNum3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        inputNum3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputNum4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        inputNum4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputNum5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        inputNum5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputNum6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void onDelete( String img) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("items").orderByChild("imageUrl").equalTo(img);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();

                }
            }

            @Override

            public void onCancelled(DatabaseError databaseError) {

                Log.e( "onCancelled", String.valueOf(databaseError.toException()));
            }
        });
    }

}
