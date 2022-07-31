package com.ieeeias.vit_finders.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ieeeias.vit_finders.R;
import com.ieeeias.vit_finders.adapter.ListItemAdapter;
import com.ieeeias.vit_finders.model.ListItem;
import com.ieeeias.vit_finders.view.AddItemActivity;
import com.ieeeias.vit_finders.view.ItemDescriptionActivity;

import java.util.ArrayList;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link LostItemsFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class LostItemsFragment extends Fragment {
    //GoogleSignInClient mGoogleSignInClient;
    private DatabaseReference mDatabaseReference;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public LostItemsFragment() {
        // Required empty public constructor
    }

    //
//    // TODO: Rename and change types and number of parameters
//    public static LostItemsFragment newInstance(String param1, String param2) {
//        LostItemsFragment fragment = new LostItemsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
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
        super.onCreate(savedInstanceState);

        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.items_list_fragment, container, false);
        ListView listView = view.findViewById(R.id.itemsList);
        ArrayList<ListItem> itemList = new ArrayList<>();
        ListItemAdapter mAdapter = new ListItemAdapter(getContext(), R.layout.list_item, itemList);

//        ImageView profile = view.findViewById(R.id.profile_image);
//        profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent= new Intent(LostItemsFragment.this.getActivity(), PersonalInfo.class);
//                startActivity(intent);
//            }
//        });

        listView.setAdapter(mAdapter);

        View emptyView = view.findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Items");
//        mStorageReference =  FirebaseStorage.getInstance().getReference();

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            private static final String TAG = "LostItemsActivity";

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ListItem listItem = snapshot.getValue(ListItem.class);
                itemList.add(listItem);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mAdapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private final String TAG = "LostItemsActivity";

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Intent intent = new Intent(LostItemsFragment.this.getActivity(), ItemDescriptionActivity.class);
                    ListItem listItem = (ListItem) listView.getItemAtPosition(i);
                    intent.putExtra("name", listItem.getNameView());
                    intent.putExtra("brand", listItem.getBrandView());
                    intent.putExtra("date", listItem.getDateView());
                    intent.putExtra("location", listItem.getLocView());
                    intent.putExtra("contact", listItem.getContactView());
                    intent.putExtra("category", listItem.getCategoryView());
                    intent.putExtra("imageUrl", listItem.getImageUrl());
                    intent.putExtra("userId", listItem.getUserId());
                    startActivity(intent);
                } catch (Exception e) {
                    Log.w(TAG, e.getMessage());
                }
            }
        });

//         Setup FAB to open AddItemActivity
        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LostItemsFragment.this.getActivity(), AddItemFragment.class);
                startActivity(intent);
            }
        });

//        SearchView searchView = findViewById(R.id.searchBar);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                mAdapter.getFilter().filter(s);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                mAdapter.getFilter().filter(s);
//                return false;
//            }
//        });

//        AutoCompleteTextView searchBar = findViewById(R.id.searchBar);
//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    for(DataSnapshot ds: snapshot.getChildren()){
//                        ListItem listItem = ds.child("items").getValue(ListItem.class);
//                        itemList.add(listItem);
//                    }
//                }
//                else{
//                    Log.w("LostItemsActivity", "no items found");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        }

        SearchView searchView = view.findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<ListItem> filtered = new ArrayList<ListItem>();
                for (ListItem listItem : itemList) {
                    if (listItem.getNameView().toLowerCase().contains(s.toLowerCase())) {
                        filtered.add(listItem);
                    }
                }
                ListItemAdapter adapter = new ListItemAdapter(getActivity(), 0, filtered);
                listView.setAdapter(adapter);
                return false;
            }
        });
        return view;
    }
}