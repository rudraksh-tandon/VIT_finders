package com.ieeeias.vit_finders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LostItemsActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseReference mDatabaseReference;
//    private StorageReference mStorageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.lost_items);

        listView = findViewById(R.id.itemsList);

        ArrayList<ListItem> itemList = new ArrayList<>();
        ListItemAdapter mAdapter = new ListItemAdapter(this, R.layout.list_item, itemList);

        ImageView profile = findViewById(R.id.profile_image);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LostItemsActivity.this, PersonalInfo.class);
                startActivity(intent);
            }
        });

        listView.setAdapter(mAdapter);

        View emptyView = findViewById(R.id.empty_view);
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
                    Intent intent = new Intent(LostItemsActivity.this, ItemDescriptionActivity.class);
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
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LostItemsActivity.this, AddItemActivity.class);
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
    }
}
