//package com.ieeeias.vit_finders;
//
//import android.annotation.SuppressLint;
//import android.app.DatePickerDialog;
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//
//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.MimeTypeMap;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.github.drjacky.imagepicker.ImagePicker;
//import com.github.drjacky.imagepicker.constant.ImageProvider;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.Calendar;
//
//import kotlin.Unit;
//import kotlin.jvm.functions.Function1;
//import kotlin.jvm.internal.Intrinsics;
////
///////**
////// * A simple {@link Fragment} subclass.
////// * Use the {@link BlankFragment3#newInstance} factory method to
////// * create an instance of this fragment.
////// */
//public class BlankFragment3 extends Fragment {
//    private ImageView imageView;
//    private ImageButton imageButton;
//    private Uri imageUri;
//    private DatabaseReference mDatabaseReference;
//    private StorageReference mStorageReference;
//    private NewItem newItem;
//    private String newItemId;
//    PrefManager prefManager;
//    TextView date, category;
//    int year, mon, day;
//    ImageView profile;
//    Spinner catSpinner;
//    ActivityResultLauncher<Intent> launcher =
//            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
//                if (result.getResultCode() == RESULT_OK) {
//                    imageUri = result.getData().getData();
//                    imageView.setImageURI(imageUri);
//                    // Use the uri to load the image
//                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
//                    ImagePicker.Companion.getError(result.getData());
//                }
//            });
//
////    // TODO: Rename parameter arguments, choose names that match
////    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//////    private static final String ARG_PARAM1 = "param1";
//////    private static final String ARG_PARAM2 = "param2";
////
////    // TODO: Rename and change types of parameters
//////    private String mParam1;
//////    private String mParam2;
////
////    public BlankFragment3() {
////        // Required empty public constructor
////    }
////
//////    /**
//////     * Use this factory method to create a new instance of
//////     * this fragment using the provided parameters.
//////     *
//////     * @param param1 Parameter 1.
//////     * @param param2 Parameter 2.
//////     * @return A new instance of fragment BlankFragment3.
//////     */
////    // TODO: Rename and change types and number of parameters
//////    public static BlankFragment3 newInstance(String param1, String param2) {
//////        BlankFragment3 fragment = new BlankFragment3();
//////        Bundle args = new Bundle();
//////        args.putString(ARG_PARAM1, param1);
//////        args.putString(ARG_PARAM2, param2);
//////        fragment.setArguments(args);
//////        return fragment;
//////    }
////
//////    @Override
//////    public void onCreate(Bundle savedInstanceState) {
//////        super.onCreate(savedInstanceState);
//////        if (getArguments() != null) {
//////            mParam1 = getArguments().getString(ARG_PARAM1);
//////            mParam2 = getArguments().getString(ARG_PARAM2);
//////        }
//////    }
////
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        //Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_blank3, container, false);
//        prefManager = new PrefManager(getContext());
//
//        date = view.findViewById(R.id.editDateView);
//        profile = view.findViewById(R.id.profile_image);
//        profile.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent = new Intent(BlankFragment3.this.getActivity(), PersonalInfo.class);
//                startActivity(intent);
//            }
//        });
//
//        ImageView calendar = view.findViewById(R.id.calendar);
//        //ig=findViewById(R.id.ig);
//        final Calendar c = Calendar.getInstance();
//
//        calendar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                year = c.get(Calendar.YEAR);
//                mon = c.get(Calendar.MONTH);
//                day = c.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog g = new DatePickerDialog(BlankFragment3.this.getActivity(), new DatePickerDialog.OnDateSetListener() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                        i1 = i1 + 1;
//                        date.setText(i2 + "/" + i1 + "/" + i);
//
//                    }
//                }, year, mon, day);
//                g.show();
//
//            }
//        });
//
//        category = view.findViewById(R.id.CategoryView);
//        catSpinner = view.findViewById(R.id.categorySpinner);
//        setupSpinner();
//
//        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Items");
//        mStorageReference = FirebaseStorage.getInstance().getReference("Items");
//
//        imageView = (ImageView) view.findViewById(R.id.imageView);
//        imageButton = (ImageButton) view.findViewById(R.id.imageButton);
//
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ImagePicker.Companion.with(BlankFragment3.this.getActivity())
//                        .crop()
//                        .cropOval()
//                        .maxResultSize(512, 512, true)
//                        .provider(ImageProvider.BOTH)
//                        .createIntentFromDialog(new Function1() {
//                            public Object invoke(Object var1) {
//                                this.invoke((Intent) var1);
//                                return Unit.INSTANCE;
//                            }
//
//                            public final void invoke(@NotNull Intent it) {
//                                Intrinsics.checkNotNullParameter(it, "it");
//                                launcher.launch(it);
//                            }
//                        });
//
//            }
//        });
//
//        Button submitButton = (Button) view.findViewById(R.id.button);
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                ListItem listItem = new ListItem()
//                if (imageUri != null) {
//                    uploadImage(imageUri);
//                    Toast.makeText(BlankFragment3.this.getActivity(), "Upload successful", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(BlankFragment3.this.getActivity(), MainScreenActivity.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(BlankFragment3.this.getActivity(), "Please upload image", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//
////        private void setupSpinner () {
////            ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(getContext(),
////                    R.array.array_category_options, android.R.layout.simple_spinner_item);
////
////            categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
////
////            catSpinner.setAdapter(categorySpinnerAdapter);
////            catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////                @Override
////                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
////                    String selection = (String) adapterView.getItemAtPosition(position);
////                    if (!TextUtils.isEmpty(selection)) {
////                        category.setText(selection);
////
////                    }
////                }
////
////                @Override
////                public void onNothingSelected(AdapterView<?> adapterView) {
////                    category.setText(getString(R.string.category_others));
////
////                }
////
////            });
////        }
//
//
//
//            private String getName () {
//                EditText nameView = view.findViewById(R.id.editNameView);
//                return nameView;
//
//            }
//            private String getBrand () {
//                EditText brandView = view.findViewById(R.id.editBrandView);
//                return brandView;
//            }
//            private String getLocation () {
//                EditText locView = view.findViewById(R.id.editLocView);
//                return locView;
//            }
//            private String getContact () {
//                EditText contactView = view.findViewById(R.id.editContactView);
//                return contactView;
//            }
//            private String getDate () {
//                @SuppressLint("CutPasteId") EditText dateView = view.findViewById(R.id.editDateView);
//                return dateView;
//            }
//
//            private String getCategory () {
//                @SuppressLint("CutPasteId") TextView categoryView = view.findViewById(R.id.CategoryView);
//                return categoryView;
//            }
//
//
////        private void uploadImage(Uri uri){
////            ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(getContext(),
////                    R.array.array_category_options, android.R.layout.simple_spinner_item);
////
////            categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
////
////            catSpinner.setAdapter(categorySpinnerAdapter);
////            catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////                @Override
////                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
////                    String selection = (String) adapterView.getItemAtPosition(position);
////                    if (!TextUtils.isEmpty(selection)) {
////                        category.setText(selection);
////
////                    }
////                }
////
////                @Override
////                public void onNothingSelected(AdapterView<?> adapterView) {
////                    category.setText(getString(R.string.category_others));
////
////                }
////
////            });
////        }
//
//
//
//
//
//            private String getFileExtension (Uri uri1){
//                    ContentResolver cr = getContentResolver();
//                    MimeTypeMap mime = MimeTypeMap.getSingleton();
//
//                    return mime.getExtensionFromMimeType(cr.getType(uri1));
//
//
//
//
//            }
//
//        }
//
//    private ContentResolver getContentResolver() {
//    }
//
//    private void uploadImage(Uri imageUri) {
//        ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.array_category_options, android.R.layout.simple_spinner_item);
//
//        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//
//        catSpinner.setAdapter(categorySpinnerAdapter);
//        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                String selection = (String) adapterView.getItemAtPosition(position);
//                if (!TextUtils.isEmpty(selection)) {
//                    category.setText(selection);
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                category.setText(getString(R.string.category_others));
//
//            }
//
//        });
//    }
//
//    private void setupSpinner() {
//        ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.array_category_options, android.R.layout.simple_spinner_item);
//
//        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//
//        catSpinner.setAdapter(categorySpinnerAdapter);
//        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                String selection = (String) adapterView.getItemAtPosition(position);
//                if (!TextUtils.isEmpty(selection)) {
//                    category.setText(selection);
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
// |               category.setText(getString(R.string.category_others));
//
//            }
//
//        });
//
//
//    }
//
//}
//
//
//
//
//
//
//    }
//            return view;
//
//
//}
//
//
//
//
//
//
//
