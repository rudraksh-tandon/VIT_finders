package com.ieeeias.vit_finders.model;

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
    private String categoryView;
    private String userId;

    public ListItem(){

    }


    public ListItem(String imageUrl, String nameView, String locView, String brandView, String dateView, String contactView, String categoryView, String userId) {
        this.imageUrl = imageUrl;
        this.nameView = nameView;
        this.locView = locView;
        this.brandView = brandView;
        this.dateView = dateView;
        this.contactView = contactView;
        this.categoryView = categoryView;
        this.userId = userId;
    }

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
    public String getUserId() {
        return userId;
    }
}
