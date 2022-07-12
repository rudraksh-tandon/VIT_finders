package com.ieeeias.vit_finders;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URL;

public class ListItem{

    private String imageUrl;
    private String nameView;
    private String locView;
    private String brandView;
    private String dateView;
    private String contactView;
    public String categoryView;

    public ListItem(){

    }


    public ListItem(String imageUrl, String nameView, String locView, String brandView, String dateView, String contactView, String categoryView) {

        this.imageUrl = imageUrl;
        this.nameView = nameView;
        this.locView = locView;
        this.brandView = brandView;
        this.dateView = dateView;
        this.contactView = contactView;
        this.categoryView = categoryView;
    }

//    public int getImageView() {
//        return imageView;
//    }
//
//    public void setImageView(int imageView) {
//        this.imageView = imageView;
//    }

    public String getImageUrl() {
        return imageUrl;
    }
    public String getNameView() {
        return nameView;
    }
    public String getLocView() {
        return locView;
    }
    public String getBrandView() {
        return brandView;
    }
    public String getDateView() {
        return dateView;
    }
    public String getContactView() {
        return contactView;
    }
    public String getCategoryView() {
        return categoryView;
    }
}
