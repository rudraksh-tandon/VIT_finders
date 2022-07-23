package com.ieeeias.vit_finders;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
public class BlankFragment1 extends AppCompatActivity  {
    BottomNavigationView bottomNavigationView;
    TextView tt;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bottomNavigationView=findViewById(R.id.hello);
        bottomNavigationView.setSelectedItemId(R.id.hello);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.aboutUs:
                        //tt.setText("Raghav I have done");
                        Toast.makeText(BlankFragment1.this, "DONE SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}






