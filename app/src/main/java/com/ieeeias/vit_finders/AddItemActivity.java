package com.ieeeias.vit_finders;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    private static final int PIC_ID = 1;
    Bitmap photo;
//    private ListItemAdapter mListItemAdapter;
//    private ListView mListItemView;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mItemDatabaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.add_item);

//        mListItemView = (ListView) findViewById(R.id.itemsList);

        // Initialize message ListView and its adapter
//        List<ListItem> listItems = new ArrayList<>();
//        mListItemAdapter = new ListItemAdapter(this, R.layout.list_item, listItems);
//        mListItemView.setAdapter(mListItemAdapter);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mItemDatabaseReference = mFirebaseDatabase.getReference().child("items");

        Button submitButton = (Button) findViewById(R.id.button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ListItem listItem = new ListItem()
                NewItem newItem = new NewItem(1, getName(), getBrand(), getDate(), getLocation(), getContact());
                mItemDatabaseReference.push().setValue(newItem);
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
