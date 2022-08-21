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
import android.widget.ProgressBar;

import androidx.appcompat.widget.SearchView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ieeeias.vit_finders.R;
import com.ieeeias.vit_finders.adapter.ListItemAdapter;
import com.ieeeias.vit_finders.model.ListItem;
import com.ieeeias.vit_finders.view.ItemDescriptionActivity;

import java.util.ArrayList;

public class LostItemsFragment extends Fragment {
    private DatabaseReference mDatabaseReference;

    public LostItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.items_list_fragment, container, false);
        ListView listView = view.findViewById(R.id.itemsList);
        ArrayList<ListItem> itemList = new ArrayList<>();
        ListItemAdapter mAdapter = new ListItemAdapter(getContext(), R.layout.list_item, itemList);

        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        listView.setAdapter(mAdapter);

        View emptyView = view.findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Items");

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            private static final String TAG = "LostItemsActivity";

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ListItem listItem = snapshot.getValue(ListItem.class);
                itemList.add(listItem);
                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private final String TAG = "LostItemsActivity";

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Intent intent = new Intent(getActivity(), ItemDescriptionActivity.class);
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