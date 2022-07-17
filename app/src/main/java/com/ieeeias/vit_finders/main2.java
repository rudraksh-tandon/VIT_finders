package com.ieeeias.vit_finders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class main2 extends AppCompatActivity {
    BottomNavigationView bnview;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);

        bnview = findViewById(R.id.bottomNavigationView);

        bnview.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    loadfragment(new home1(), false);
                } else if (id == R.id.nav_home1) {

                    loadfragment(new BlankFragment2(), false);

                } else if (id == R.id.nav_home2) {
                    loadfragment(new BlankFragment2(), false);

                } else if (id == R.id.nav_home3) {
                    loadfragment(new BlankFragment4(), false);

                } else {
                    loadfragment(new BlankFragment5(), true);

                }
                return true;

            }

        });
        bnview.setSelectedItemId(R.id.nav_home);


    }


    public void loadfragment(Fragment frg,boolean flag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(flag){
            fragmentTransaction.add(R.id.container,frg);

        }
        else
        {
            fragmentTransaction.replace(R.id.container,frg);
        }

        fragmentTransaction.commit();
    }
            }








