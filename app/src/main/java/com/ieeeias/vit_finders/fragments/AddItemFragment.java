package com.ieeeias.vit_finders.fragments;

import static android.app.Activity.RESULT_OK;
import static com.google.common.io.Files.getFileExtension;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.fragment.app.Fragment;

import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ieeeias.vit_finders.R;
import com.ieeeias.vit_finders.model.NewItem;
import com.ieeeias.vit_finders.utils.PrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class AddItemFragment extends Fragment {
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

    public AddItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.add_item_fragment, container, false);
        prefManager = new PrefManager(AddItemFragment.this.requireActivity());

        date = view.findViewById(R.id.editDateView);
        category = view.findViewById(R.id.CategoryView);
        TextView dateView = view.findViewById(R.id.editDateView);

        ImageView calendar =view.findViewById(R.id.calendar);
        final Calendar c = Calendar.getInstance();

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = c.get(Calendar.YEAR);
                mon = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog g = new DatePickerDialog(AddItemFragment.this.getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1 = i1 + 1;

                        dateView.setText(i2 + "/" + i1 + "/" + i);

                    }
                }, year, mon, day);
               g.getDatePicker().setMaxDate(c.getTimeInMillis());
                g.show();

            }
        });


        catSpinner = view.findViewById(R.id.categorySpinner);
        setupSpinner();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Items");
        mStorageReference = FirebaseStorage.getInstance().getReference("Items");

        imageView = (ImageView) view.findViewById(R.id.imageView);
        imageButton = (ImageButton) view.findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(AddItemFragment.this.getActivity())
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

        Button submitButton = (Button) view.findViewById(R.id.button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ListItem listItem = new ListItem()
                if (imageUri != null) {
                    uploadImage(imageUri);
//                    Toast.makeText(AddItemFragment.this.getActivity(), "Upload successful", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddItemFragment.this.getActivity(), "Please upload image", Toast.LENGTH_LONG).show();
                }
            }

            private void uploadImage(Uri uri) {
                StorageReference ref = mStorageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri.toString()));
                ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if(!checkValidContact()){
                                    Toast.makeText(getContext(), "Please enter valid contact number", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    newItem = new NewItem(uri.toString(), getName(), getBrand(), getDate(), getLocation(), getContact(), getCategory(), prefManager.getId());
                                    newItemId = mDatabaseReference.push().getKey();
                                    mDatabaseReference.child(newItemId).setValue(newItem);
                                    Toast.makeText(getContext(), "Item added successfully", Toast.LENGTH_LONG).show();
                                    resetFragment();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddItemFragment.this.getActivity(), "Uploading failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return view;
    }

    private void setupSpinner() {
        ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(AddItemFragment.this.getActivity(),
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

        private String getName () {
            EditText nameView = getView().findViewById(R.id.linearLayoutName);
            return nameView.getText().toString();
        }
        private String getBrand () {
            EditText brandView = getView().findViewById(R.id.linearLayoutBrand);
            return brandView.getText().toString();
        }
        private String getLocation () {
            EditText locView = getView().findViewById(R.id.linearLayoutLocation);
            return locView.getText().toString();
        }
        private String getContact () {
            EditText contactView = getView().findViewById(R.id.linearLayoutContact);
            return contactView.getText().toString();
        }
        private String getDate () {
            TextView dateView = getView().findViewById(R.id.editDateView);
            return dateView.getText().toString();
        }
        private String getCategory () {
            TextView categoryView = getView().findViewById(R.id.CategoryView);
            return categoryView.getText().toString();
        }

        private Boolean checkValidContact () {
            Pattern p = Pattern.compile("^[6-9][0-9]{9}$");
            Matcher m = p.matcher(getContact());
            return m.matches();
        }

        private void resetFragment () {
            imageView.setBackground(null);
            category.setText(getString(R.string.category_others));
            EditText nameView = getView().findViewById(R.id.linearLayoutName);
            nameView.setText("");
            EditText brandView = getView().findViewById(R.id.linearLayoutBrand);
            brandView.setText("");
            EditText locView = getView().findViewById(R.id.linearLayoutLocation);
            locView.setText("");
            EditText contactView = getView().findViewById(R.id.linearLayoutContact);
            contactView.setText("");
            TextView dateView = getView().findViewById(R.id.editDateView);
            dateView.setText("");
        }
}