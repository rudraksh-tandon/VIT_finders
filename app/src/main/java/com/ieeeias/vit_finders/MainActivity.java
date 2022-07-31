package com.ieeeias.vit_finders;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ieeeias.vit_finders.model.User;
import com.ieeeias.vit_finders.utils.PrefManager;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    PrefManager prefManager;
    GoogleSignInClient mGoogleSignInClient;
    Button signInButton;

    private static int RC_SIGN_IN = 1000;
    private final int updatereqcode = 1600;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        prefManager = new PrefManager(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton = findViewById(R.id.button);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

//        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        appupdate();
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        appupdate();
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            String result = acct.getEmail();
            boolean bool = result.contains("@vitstudent");

            if (bool) {
                prefManager.isLogin(true);
                AddUser();
                Toast.makeText(MainActivity.this, "SignIn Successful", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, main2.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(MainActivity.this, "Access denied Please use VIT MAIL ID", Toast.LENGTH_LONG).show();
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
//                                    Toast.makeText(MainActivity.this,"Signout successful",Toast.LENGTH_LONG).show();
                            }
                        });
            }
        } catch (ApiException e) {
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void appupdate() {
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);

// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
                try {


                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE
                            , MainActivity.this, updatereqcode);
                } catch (IntentSender.SendIntentException exception) {
                    Toast.makeText(this, "Calling in app update", Toast.LENGTH_SHORT).show();
                    signIn();
                }
            }
        });

    }



    protected void onActivityResult1(int requestcc, int resultcode,@Nullable Intent data) {

        super.onActivityResult(requestcc, resultcode, data);
        if (data == null) return;
        if (requestcc == updatereqcode) {
            Toast.makeText(this, "Downloading started", Toast.LENGTH_LONG).show();

        }
        if (requestcc == updatereqcode) {
            Toast.makeText(this, "failed to make request", Toast.LENGTH_LONG).show();

        }

    }

    private void AddUser() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getGivenName();
            String regNo = acct.getFamilyName();
            String email = acct.getEmail();
            String userId = acct.getId();
            prefManager.setId(userId);

            DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
            User user = new User(userId, personName, email, regNo);
//            String newUserId = mDatabaseReference.push().getKey();
            mDatabaseReference.child(userId).setValue(user);
        }
    }
}