package com.ieeeias.vit_finders.fragments;

import static android.app.Activity.RESULT_OK;
import static com.google.common.io.Files.getFileExtension;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.ieeeias.vit_finders.view.MainScreenActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link BlankFragment7#newInstance} factory method to
// * create an instance of this fragment.
// */
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
    //    ImageView profile;
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
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public AddItemFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment BlankFragment7.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static BlankFragment7 newInstance(String param1, String param2) {
//        BlankFragment7 fragment = new BlankFragment7();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_item, container, false);
        prefManager = new PrefManager(AddItemFragment.this.getActivity());

        date = view.findViewById(R.id.editDateView);
//        profile = view.findViewById(R.id.profile_image);
//        profile.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent = new Intent(AddItemFragment.this.getActivity(), PersonalInfo.class);
//                startActivity(intent);
//            }
//        });

        ImageView calendar =view.findViewById(R.id.calendar);
        //ig=findViewById(R.id.ig);
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
                        date.setText(i2 + "/" + i1 + "/" + i);

                    }
                }, year, mon, day);
                g.show();

            }
        });

        category = view.findViewById(R.id.CategoryView);
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
                    Toast.makeText(AddItemFragment.this.getActivity(), "Upload successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddItemFragment.this.getActivity(), MainScreenActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddItemFragment.this.getActivity(), "Please upload image", Toast.LENGTH_LONG).show();
                }
            }

//            String name, brand, category, contact, loc, date;
//            EditText name
            EditText nameView = view.findViewById(R.id.editNameView);
            String name = nameView.getText().toString();
            EditText brandView = view.findViewById(R.id.editBrandView);
            String brand = brandView.getText().toString();
            EditText dateView = view.findViewById(R.id.editDateView);
            String date = dateView.toString();
            EditText categoryView = view.findViewById(R.id.categoryView);
            String category = categoryView.toString();
            EditText locView = view.findViewById(R.id.editLocView);
            String loc = locView.toString();
            EditText contactView = view.findViewById(R.id.editContactView);
            String contact = contactView.getText().toString();

            private void uploadImage(Uri uri) {
                StorageReference ref = mStorageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri.toString()));
                ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
//                        imageUrl = uri.toString();
//                        model = new Model(imageUrl);
                                newItem = new NewItem(uri.toString(), name, brand, date, loc, contact, category, prefManager.getId());
                                newItemId = mDatabaseReference.push().getKey();
                                mDatabaseReference.child(newItemId).setValue(newItem);
                            }

//                            private String getCategory() {
//                                EditText brandView = view.findViewById(R.id.editBrandView);
//                                return brandView.toString();
//
//                            }
//
//                            private String getContact() {
//                                EditText contactView = view.findViewById(R.id.editContactView);
//                                return contactView.toString();
//                            }
//
//                            private String getLocation() {
//                                EditText locView = view.findViewById(R.id.editLocView);
//                                return locView.toString();
//                            }
//
//                            private String getDate() {
//                                EditText dateView =view. findViewById(R.id.editDateView);
//                                return dateView.getText().toString();
//                            }
//
//                            private String getBrand() {
//                                EditText brandView = view.findViewById(R.id.editBrandView);
//                                return brandView.toString();
//                            }
//
//                            private String getName() {
//                                EditText nameView = view.findViewById(R.id.editNameView);
//                                return nameView.toString();
//
//                            }
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
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view1 = inflater.inflate(R.layout.fragment_blank7, container, false);
//
//
////        private String getName () {
////            EditText nameView = view1.findViewById(R.id.editNameView);
////            return nameView;
////        }
////        private String getBrand () {
////            EditText brandView = view1.findViewById(R.id.editBrandView);
////            return brandView;
////        }
////        private String getLocation () {
////            EditText locView = view1.findViewById(R.id.editLocView);
////            return locView;
////        }
////        private String getContact () {
////            EditText contactView = view1.findViewById(R.id.editContactView);
////            return contactView;
////        }
////        private String getDate () {
////            EditText dateView =view1. findViewById(R.id.editDateView);
////            return dateView.getText().toString();
////        }
////
////        private String getCategory () {
////            TextView categoryView =view1. findViewById(R.id.CategoryView);
////            return categoryView.getText().toString();
////        }
//
//        private void uploadImage (Uri uri){
//            StorageReference ref = mStorageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
//            ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                            public void onSuccess(Uri uri) {
////                        imageUrl = uri.toString();
////                        model = new Model(imageUrl);
//                            newItem = new NewItem(uri.toString(), getName(), getBrand(), getDate(), getLocation(), getContact(), getCategory(), prefManager.getId());
//                            newItemId = mDatabaseReference.push().getKey();
//                            mDatabaseReference.child(newItemId).setValue(newItem);
//                        }
//
//                        private String getCategory() {
//                            EditText brandView = view1.findViewById(R.id.editBrandView);
//                           return brandView.toString();
//                        }
//
//                        private String getContact() {
//                            EditText contactView = view1.findViewById(R.id.editContactView);
//                             return contactView.toString();
//                        }
//
//                        private String getLocation() {
//                            EditText locView = view1.findViewById(R.id.editLocView);
//                            return locView.toString();
//                        }
//
//                        private String getDate() {
//                            EditText dateView =view1. findViewById(R.id.editDateView);
//                             return dateView.getText().toString();
//                        }
//
//                        private String getBrand() {
//                            EditText brandView = view1.findViewById(R.id.editBrandView);
//                             return brandView.toString();
//                        }
//
//                        private String getName() {
//                            EditText nameView = view1.findViewById(R.id.editNameView);
//                            return nameView.toString();
//                        }
//                    });
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(BlankFragment7.this.getActivity(), "Uploading failed", Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//
//        private String getFileExtension (Uri uri){
//            ContentResolver cr = getContentResolver();
//            MimeTypeMap mime = MimeTypeMap.getSingleton();
//            return mime.getExtensionFromMimeType(cr.getType(uri));
//        }
//
//    }
//
//    private ContentResolver getContentResolver() {
//    }


}