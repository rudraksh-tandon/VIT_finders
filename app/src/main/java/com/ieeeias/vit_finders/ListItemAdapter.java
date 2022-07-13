package com.ieeeias.vit_finders;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class ListItemAdapter extends ArrayAdapter<ListItem> {

    URL imageUrl;
    public ListItemAdapter(Context context, int resource, List<ListItem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.list_item, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        TextView nameView = (TextView) convertView.findViewById(R.id.name);
        TextView locView = (TextView) convertView.findViewById(R.id.loc);

        ListItem item = getItem(position);

        try {
            URI imageUri = new URI(item.getImageUrl());
            imageUrl = imageUri.toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }
        Glide.with(getContext()).load(imageUrl).into(imageView);
        nameView.setText(item.getNameView());
        locView.setText(item.getLocView());

        return convertView;
    }
}