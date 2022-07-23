package com.ieeeias.vit_finders.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ieeeias.vit_finders.MainActivity;
import com.ieeeias.vit_finders.R;

public class MainScreenActivity extends AppCompatActivity {
    Button signOut;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.main_screen);

        TextView lostText = findViewById(R.id.lost_text);
        lostText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreenActivity.this, LostItemsActivity.class);
                startActivity(intent);
            }
        });

        TextView foundText = findViewById(R.id.found_text);
        foundText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreenActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });

        signOut = findViewById(R.id.signout_button);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
//                Toast.makeText(MainScreenActivity.this,"SignOut successful",Toast.LENGTH_LONG).show();
                Intent intent= new Intent(MainScreenActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });


    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainScreenActivity.this,"SignOut successful",Toast.LENGTH_LONG).show();
                    }
                });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(MainScreenActivity.this,MainActivity.class);
        startActivity(intent);
    }

}
