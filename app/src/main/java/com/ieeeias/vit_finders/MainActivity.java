package com.ieeeias.vit_finders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
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

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
//    RecyclerView recyclerView;
//    LinearLayoutManager layoutManager;
//    List<ListItem>itemList;
//    Adapter adapter;

    GoogleSignInClient mGoogleSignInClient;
    Button sg;
    Button signInButton ;

    private static int RC_SIGN_IN=1000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton=findViewById(R.id.button);

        sg=findViewById(R.id.sg);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        // Set the dimensions of the sign-in button.

//        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }
//    private void iniData() {
//        itemList=new ArrayList<>(); //we can also add data from firebase
//        itemList.add(new ListItem(R.drawable.lost_item_img,"speaker","SJT"));
//        itemList.add(new ListItem(R.drawable.lost_item_img,"speaker","SJT"));
//        itemList.add(new ListItem(R.drawable.lost_item_img,"speaker","SJT"));
//
//
//
//    }
//    private void iniRecyclerView() {
//
//        recyclerView=findViewById(R.id.rview);
//        layoutManager=new LinearLayoutManager(this);
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//      adapter = (Adapter) new ListItemAdapter(itemList);
//    recyclerView.setAdapter((RecyclerView.Adapter) adapter);
//    ((RecyclerView.Adapter<?>) adapter).notifyDataSetChanged();
//
//    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            String result = acct.getEmail();
            boolean bool = result.contains("@vitstudent");
            Toast.makeText(MainActivity.this, "successfull", Toast.LENGTH_LONG).show();

            System.out.println(bool);
            System.out.println(result);

            if(bool==true) {
                Intent intent = new Intent(MainActivity.this, MainScreenActivity.class);
                startActivity(intent);
               Toast.makeText(MainActivity.this, "Signin Successfull", Toast.LENGTH_LONG).show();
                signInButton.setVisibility(View.VISIBLE);
               sg.setVisibility(View.INVISIBLE);
            }
            else{
//                Intent intent = new Intent(MainActivity.this, personalinfo.class);
//                startActivity(intent);
//                Toast.makeText(MainActivity.this, "Signin Successfull", Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, "Access denied Please use VIT MAIL ID", Toast.LENGTH_LONG).show();
                signInButton.setVisibility(View.INVISIBLE);
                sg.setVisibility(View.VISIBLE);




            }

            // Signed in successfully, show authenticated UI.



        } catch (ApiException e){
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());
            sg.setVisibility(View.VISIBLE);
        }
        sg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this,"signout sucessfull",Toast.LENGTH_LONG).show();
                        // ...
                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sg.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.INVISIBLE);
    }
}
