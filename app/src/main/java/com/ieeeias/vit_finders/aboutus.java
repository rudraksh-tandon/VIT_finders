package com.ieeeias.vit_finders;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class aboutus extends AppCompatActivity {
    ImageView about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ;
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.aboutus);
        about= findViewById(R.id.about);


    }
}
