package com.ieeeias.vit_finders;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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
    private ImageView imageView;
    private ImageButton imageButton;
    private Uri imageUri;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;
    private NewItem newItem;
    private String newItemId;
    PrefManager prefManager;

    TextView date, category;
    int year, mon, day;
    ImageView profile;
    Spinner catSpinner;

    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                if (result.getResultCode() == RESULT_OK) {
                    imageUri = result.getData().getData();
                    imageView.setImageURI(imageUri);
                    // Use the uri to load the image
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                     ImagePicker.Companion.getError(result.getData());
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.add_item);
        prefManager = new PrefManager(this);

        date = findViewById(R.id.editDateView);
        profile = findViewById(R.id.profile_image);
        profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent= new Intent(AddItemActivity.this, PersonalInfo.class);
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

        category = findViewById(R.id.CategoryView);
        catSpinner = findViewById(R.id.categorySpinner);
        setupSpinner();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Items");
        mStorageReference =  FirebaseStorage.getInstance().getReference("Items");

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
                    Toast.makeText(AddItemActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddItemActivity.this, MainScreenActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(AddItemActivity.this, "Please upload image", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setupSpinner(){
        ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_category_options, android.R.layout.simple_spinner_item);

        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        catSpinner.setAdapter(categorySpinnerAdapter);
        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selection = (String) adapterView.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    category.setText(selection);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                    category.setText(getString(R.string.category_others));
            }
        });
    }

    private String getName(){
        EditText nameView = findViewById(R.id.editNameView);
        return nameView.getText().toString();
    }
    private String getBrand(){
        EditText brandView = findViewById(R.id.editBrandView);
        return brandView.getText().toString();
    }
    private String getLocation(){
        EditText locView = findViewById(R.id.editLocView);
        return locView.getText().toString();
    }
    private String getContact(){
        EditText contactView = findViewById(R.id.editContactView);
        return contactView.getText().toString();
    }
    private String getDate(){
        EditText dateView = findViewById(R.id.editDateView);
        return dateView.getText().toString();
    }

    private String getCategory(){
        TextView categoryView = findViewById(R.id.CategoryView);
        return categoryView.getText().toString();
    }

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
                        newItem = new NewItem(uri.toString(), getName(), getBrand(), getDate(), getLocation(), getContact(), getCategory(), prefManager.getId());
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
