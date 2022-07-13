package com.ieeeias.vit_finders;

import java.net.URL;

public class NewItem {
    //    public Model model;
    public String imageUrl;
    public String nameView;
    public String brandView;
    public String dateView;
    public String locView;
    public String contactView;
    public String categoryView;
    public String userId;

    public NewItem(){

    }

    public NewItem(String imageUrl, String nameView, String brandView, String dateView, String locView, String contactView, String categoryView, String userId) {
//        this.imageView = imageView;
//        this.model = model;
        this.imageUrl = imageUrl;
        this.nameView = nameView;
        this.brandView = brandView;
        this.dateView = dateView;
        this.locView = locView;
        this.contactView = contactView;
        this.categoryView = categoryView;
        this.userId = userId;
    }
}
