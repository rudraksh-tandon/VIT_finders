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

    public NewItem(){

    }

    public NewItem(String imageUrl, String nameView, String brandView, String dateView, String locView, String contactView) {
//        this.imageView = imageView;
//        this.model = model;
        this.imageUrl = imageUrl;
        this.nameView = nameView;
        this.brandView = brandView;
        this.dateView = dateView;
        this.locView = locView;
        this.contactView = contactView;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

//    public int getImageView() {
//        return imageView;
//    }
//
//    public void setImageView(int imageView) {
//        this.imageView = imageView;
//    }

//    public String getNameView() {
//        return nameView;
//    }
//
//    public void setNameView(String nameView){
//        this.nameView = nameView;
//    }
//
//    public String getBrandView() {
//        return brandView;
//    }
//
//    public void setBrandView(String brandView){
//        this.brandView = brandView;
//    }
}
