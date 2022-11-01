package com.ieeeias.vit_finders.fragments;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ieeeias.vit_finders.R;

public class AboutUsFragment extends Fragment {

    public AboutUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment'
        View view = inflater.inflate(R.layout.about_us_fragment, container, false);
        TextView aboutUsText = view.findViewById(R.id.aboutUsText);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            aboutUsText.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        return view;
    }
}