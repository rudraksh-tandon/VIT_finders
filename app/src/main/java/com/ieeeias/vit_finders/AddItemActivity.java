package com.ieeeias.vit_finders;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class AddItemActivity extends AppCompatActivity {
    TextView date;
    //ImageView ig;
    int year, mon, day;
    ImageView ig;

    private ImageView imageView;
    private ImageButton imageButton;
    private Uri imageUri;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;
    private NewItem newItem;
    private String newItemId;

    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                if (result.getResultCode() == RESULT_OK) {
                    imageUri = result.getData().getData();
                    imageView.setImageURI(imageUri);
                    // Use the uri to load the image
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                }
            });

//    private static final int PIC_ID = 1;
//    Bitmap photo;
//    private ListItemAdapter mListItemAdapter;
//    private ListView mListItemView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.add_item);
        date = findViewById(R.id.editDateView);
        ig=findViewById(R.id.personal);
        ig.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent= new Intent(AddItemActivity.this,personalinfo.class);
                startActivity(intent);
            }
        });
        ImageView calendar = findViewById(R.id.calendar);
        //ig=findViewById(R.id.ig);
        final Calendar c = Calendar.getInstance();
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = c.get(Calendar.YEAR);
                mon = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog g = new DatePickerDialog(AddItemActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1 + 1;
                        date.setText(i2 + "/" + i1+ "/" + i);

                    }
                }, year, mon, day);
                g.show();

            }
        });

//        mListItemView = (ListView) findViewById(R.id.itemsList);

        // Initialize message ListView and its adapter
//        List<ListItem> listItems = new ArrayList<>();
//        mListItemAdapter = new ListItemAdapter(this, R.layout.list_item, listItems);
//        mListItemView.setAdapter(mListItemAdapter);

//        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("items");
        mStorageReference =  FirebaseStorage.getInstance().getReference("items");

        imageView = (ImageView) findViewById(R.id.imageView);
        imageButton = (ImageButton) findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(AddItemActivity.this)
                        .crop()
                        .cropOval()
                        .maxResultSize(512, 512, true)
                        .provider(ImageProvider.BOTH)
                        .createIntentFromDialog(new Function1() {
                            public Object invoke(Object var1) {
                                this.invoke((Intent) var1);
                                return Unit.INSTANCE;
                            }

                            public final void invoke(@NotNull Intent it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                launcher.launch(it);
                            }
                        });

            }
        });

        Button submitButton = (Button) findViewById(R.id.button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ListItem listItem = new ListItem()
                if(imageUri != null){
                    uploadImage(imageUri);
//                    Log.e(TAG, "image url = " + imageUrl);
//                    Log.e(TAG, "model id = " + modelId);
//                    Log.e(TAG, "model = " + model.toString());
//                    newItem = new NewItem(imageUrl, getName(), getBrand(), getDate(), getLocation(), getContact());
//                    mDatabaseReference.push().setValue(newItem);
                    Toast.makeText(AddItemActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddItemActivity.this, LostItemsActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(AddItemActivity.this, "Please upload image", Toast.LENGTH_LONG).show();
                }
            }
        });

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, PIC_ID);
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startForResult.launch(intent);
//                imageChooser();
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
        EditText nameView = findViewById(R.id.editNameView);
        String name = nameView.getText().toString();
        return name;
    }
    private String getBrand(){
        EditText brandView = findViewById(R.id.editBrandView);
        String brand = brandView.getText().toString();
        return brand;
    }
    private String getLocation(){
        EditText locView = findViewById(R.id.editLocView);
        String loc = locView.getText().toString();
        return loc;
    }
    private String getContact(){
        EditText contactView = findViewById(R.id.editContactView);
        String contact = contactView.getText().toString();
        return contact;
    }
    private String getDate(){
        EditText dateView = findViewById(R.id.editDateView);
        String date = dateView.getText().toString();
        return date;
    }

//    private void imageChooser() {
//        Intent i = new Intent();
//        i.setType("image/*");
//        i.setAction(Intent.ACTION_GET_CONTENT);
//
//        launchSomeActivity.launch(i);
//    }
//
//    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            result -> {
//                if (result.getResultCode()
//                        == Activity.RESULT_OK) {
//                    Intent data = result.getData();
//                    // do your operation from here....
//                    if (data != null
//                            && data.getData() != null) {
//                        Uri selectedImageUri = data.getData();
//                        Bitmap selectedImageBitmap = null;
//                        try {
//                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(
//                                    this.getContentResolver(),
//                                    selectedImageUri);
//                        }
//                        catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        imageView.setImageBitmap(selectedImageBitmap);
//                    }
//                }
//            });

    private void uploadImage(Uri uri){
        StorageReference ref = mStorageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
//                        imageUrl = uri.toString();
//                        model = new Model(imageUrl);
                        newItem = new NewItem(uri.toString(), getName(), getBrand(), getDate(), getLocation(), getContact());
                        newItemId = mDatabaseReference.push().getKey();
                        mDatabaseReference.child(newItemId).setValue(newItem);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddItemActivity.this, "Uploading failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(AddItemActivity.this,MainScreenActivity.class);
        startActivity(intent);
    }
}
