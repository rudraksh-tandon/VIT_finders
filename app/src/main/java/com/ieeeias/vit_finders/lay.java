package com.ieeeias.vit_finders;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class lay extends AppCompatActivity {
    ImageView ig;
    TextView tt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ig=findViewById(R.id.imageView);
        tt=findViewById(R.id.textView);


}}
