package com.ieeeias.vit_finders;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URL;

public class ListItem extends AppCompatActivity {

    private String imageUrl;
    private String nameView;
    private String locView;
    private String brandView;
    private String dateView;
    private String contactView;
    ImageView ig;

    public ListItem(){

    }


    public ListItem(String imageUrl, String nameView, String locView, String brandView, String dateView, String contactView) {
        ig=findViewById(R.id.personal);
        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ListItem.this,personalinfo.class);
                startActivity(intent);
            }
        });
        this.imageUrl = imageUrl;
        this.nameView = nameView;
        this.locView = locView;
        this.brandView = brandView;
        this.dateView = dateView;
        this.contactView = contactView;



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

    public void setNameView(String nameView){
        this.nameView = nameView;
    }

    public String getLocView() {
        return locView;
    }

    public void setLocView(String locView){
        this.locView = locView;
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setBrandView(String brandView) {
        this.brandView = brandView;
    }

    public void setDateView(String dateView) {
        this.dateView = dateView;
    }

    public void setContactView(String contactView) {
        this.contactView = contactView;
    }

}
