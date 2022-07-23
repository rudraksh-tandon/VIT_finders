package com.ieeeias.vit_finders;

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
import com.ieeeias.vit_finders.fragments.AboutUsFragment;
import com.ieeeias.vit_finders.fragments.AddItemFragment;
import com.ieeeias.vit_finders.fragments.HelpFragment;
import com.ieeeias.vit_finders.fragments.LostItemsFragment;
import com.ieeeias.vit_finders.fragments.ProfileFragment;

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

                if (id == R.id.aboutUs) {
                    loadfragment(new AboutUsFragment(), false);
                } else if (id == R.id.lostItems) {
                    loadfragment(new LostItemsFragment(), false);

                } else if (id == R.id.addItem) {
                    loadfragment(new AddItemFragment(), false);

                } else if (id == R.id.help) {
                    loadfragment(new HelpFragment(), false);

                } else {
                    loadfragment(new ProfileFragment(), true);
                }
                return true;

            }

        });
        bnview.setSelectedItemId(R.id.aboutUs);
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








