package com.ieeeias.vit_finders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class AddItemActivity extends AppCompatActivity {

    private static final int PIC_ID = 1;
    Bitmap photo;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.add_item);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("Items");

        Button submitButton = (Button) findViewById(R.id.button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//        ImageView imageView = (ImageView) findViewById(R.id.imageView);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, PIC_ID);
//            }
//        });
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PIC_ID && resultCode == RESULT_OK) {
//
//            photo = (Bitmap) data.getExtras().get("data");
//            submit();
//        }
//    }

//    public void submit(){
//
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//
//        byte[] b = stream.toByteArray();
//        StorageReference storageReference =FirebaseStorage.getInstance().getReference().child("documentImages").child("noplateImg");
//        //StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_images").child(userID);
//
//        storageReference.putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                Toast.makeText(CameraActivity.this, "uploaded", Toast.LENGTH_SHORT).show();
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(CameraActivity.this,"failed",Toast.LENGTH_LONG).show();
//
//
//            }
//        });
//
//    }

    private String getName(){
        EditText nameView = findViewById(R.id.editTextName);
        String name = nameView.getText().toString();
        return name;
    }
    private String getBrand(){
        EditText brandView = findViewById(R.id.editTextBrand);
        String brand = brandView.getText().toString();
        return brand;
    }
    private String getLocation(){
        EditText locView = findViewById(R.id.editTextLocation);
        String loc = locView.getText().toString();
        return loc;
    }
    private String getContact(){
        EditText contactView = findViewById(R.id.editTextContact);
        String contact = contactView.getText().toString();
        return contact;
    }
    private String getDate(){
        EditText dateView = findViewById(R.id.editTextDate);
        String date = dateView.getText().toString();
        return date;
    }
}
