package com.ieeeias.vit_finders;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class otp2 extends AppCompatActivity {
    EditText et1;
    EditText et2;
    EditText et3;
    EditText et4;
    TextView tt;
    Button bt1;
    String otpg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.otpq);
        tt=findViewById(R.id.tt);
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        et3=findViewById(R.id.et3);
        et4=findViewById(R.id.et4);
        bt1=findViewById(R.id.so);

        //tt.setText(String.format("+91-%s",getIntent().getStringExtra("Mobile")));
        otpg=getIntent().getStringExtra("backendotp");
        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!et1.getText().toString().trim().isEmpty() && !et2.getText().toString().trim().isEmpty() && !et3.getText().toString().trim().isEmpty() && !et4.getText().toString().trim().isEmpty()){
                    String codeotp =et1.getText().toString()+et2.getText().toString()+et3.getText().toString()+et4.getText().toString();
                    if(otpg!=null){
                        PhoneAuthCredential pho= PhoneAuthProvider.getCredential(otpg,codeotp);
                        FirebaseAuth.getInstance().signInWithCredential(pho).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent=new Intent(otp2.this,AddItemActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(otp2.this, "Please Enter correct OTP", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
                    }
                    else {
                        Toast.makeText(otp2.this, "Please check your Internet connection", Toast.LENGTH_SHORT).show();

                    }
                    //Toast.makeText(otp2.this, "OTP VERIFY", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(otp2.this, "Please Enter correct OTP", Toast.LENGTH_SHORT).show();

                }


            }



        });
        numberotpmethod();

    }

    private void numberotpmethod() {
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    et2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    et3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    et4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


}
