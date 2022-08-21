package com.ieeeias.vit_finders.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ieeeias.vit_finders.R;

public class BlankFragment extends AppCompatActivity  {
    BottomNavigationView bottomNavigationView;

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
                        Toast.makeText(BlankFragment.this, "DONE SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}






