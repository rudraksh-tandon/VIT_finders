package com.ieeeias.vit_finders;

public class ListItem {
    private int imageView;
    private String nameView;
    private String locView;

    public ListItem(){

    }

    public ListItem(int imageView, String nameView, String locView) {
        this.imageView = imageView;
        this.nameView = nameView;
        this.locView = locView;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getNameView() {
        return nameView;
    }

    public void setNameView(String nameView){
        this.nameView = nameView;
    }

    public String getLocView() {
        return locView;
    }

    public void setLocView(String locView){
        this.locView = locView;
    }
}
