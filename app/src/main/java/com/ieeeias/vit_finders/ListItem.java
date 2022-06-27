package com.ieeeias.vit_finders;

import java.net.URL;

public class ListItem {
    private String imageUrl;
    private String nameView;
    private String locView;
    private String brandView;
    private String dateView;
    private String contactView;

    public ListItem(){

    }

    public ListItem(String imageUrl, String nameView, String locView) {
        this.imageUrl = imageUrl;
        this.nameView = nameView;
        this.locView = locView;
//        this.brandView = brandView;
//        this.dateView = dateView;
//        this.contactView = contactView;


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

//    public void setNameView(String nameView){
//        this.nameView = nameView;
//    }

    public String getLocView() {
        return locView;
    }

//    public void setLocView(String locView){
//        this.locView = locView;
//    }
//    public String getBrandView() {
//    return brandView;
//}
//    public String getDateView() {
//        return dateView;
//    }
//    public String getContactView() {
//        return contactView;
//    }
}
