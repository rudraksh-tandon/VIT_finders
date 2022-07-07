//package com.ieeeias.vit_finders;
//
//import android.content.ClipData;
//import android.content.ContentUris;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Parcelable;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//import android.widget.SearchView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//
//import java.util.ArrayList;
//
//public class LostItemsActivity extends AppCompatActivity {
//
//    private ListView listView;
//    private DatabaseReference mDatabaseReference;
//    ImageView ig;
////    private StorageReference mStorageReference;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
//        setContentView(R.layout.lost_items);
//
//        listView = findViewById(R.id.itemsList);
//        ig=findViewById(R.id.personal);
//        ig.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent= new Intent(LostItemsActivity.this,PersonalInfo.class);
//                startActivity(intent);
//            }
//        });
//
//        ArrayList<ListItem> itemList = new ArrayList<>();
//        ListItemAdapter mAdapter = new ListItemAdapter(this, R.layout.list_item, itemList);
//        listView.setAdapter(mAdapter);
//
//        View emptyView = findViewById(R.id.empty_view);
//        listView.setEmptyView(emptyView);
//
//        mDatabaseReference = FirebaseDatabase.getInstance().getReference("items");
////        mStorageReference =  FirebaseStorage.getInstance().getReference();
//
//        mDatabaseReference.addChildEventListener(new ChildEventListener() {
//            private static final String TAG = "LostItemsActivity";
//
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                ListItem listItem = snapshot.getValue(ListItem.class);
//                itemList.add(listItem);
////                Log.w(TAG, itemList);
//                mAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                mAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                mAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                mAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                mAdapter.notifyDataSetChanged();
//            }
//        });
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            private final String TAG = "LostItemsActivity";
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                try {
//                    Intent intent = new Intent(LostItemsActivity.this, ItemDescriptionActivity.class);
//                    ListItem listItem = (ListItem) listView.getItemAtPosition(i);
//                    intent.putExtra("name", listItem.getNameView());
//                    intent.putExtra("brand", listItem.getBrandView());
//                    intent.putExtra("date", listItem.getDateView());
//                    intent.putExtra("location", listItem.getLocView());
//                    intent.putExtra("contact", listItem.getContactView());
//                    intent.putExtra("imageUrl", listItem.getImageUrl());
//                    startActivity(intent);
//                } catch (Exception e) {
//                    Log.w(TAG, e.getMessage());
//                }
////                String key = mDatabaseReference.child("items").getKey();
////                intent.putExtra("listItem", itemList);
//////                intent.putExtra("key", key);
//////                startActivity(intent);
////                Log.w(TAG, "key = " + key);
//            }
//        });
//
//        // Setup FAB to open AddItemActivity
////        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent intent = new Intent(LostItemsActivity.this, AddItemActivity.class);
////                startActivity(intent);
////            }
////        });
//    }
//
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        getMenuInflater().inflate(R.menu.search, menu);
////        MenuItem item = menu.findItem(R.id.search);
////        SearchView searchView = (SearchView) item.getActionView();
////
////        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
////            @Override
////            public boolean onQueryTextSubmit(String s) {
////                return false;
////            }
////
////            @Override
////            public boolean onQueryTextChange(String s) {
////                return false;
////            }
////        });
////
////        return super.onCreateOptionsMenu(menu);
////    }
////
////    private void textSearch(String str){
////
////    }
//@Override
//public void onBackPressed() {
//    super.onBackPressed();
//    Intent intent= new Intent(LostItemsActivity.this,MainScreenActivity.class);
//    startActivity(intent);
//}
//}

package com.ieeeias.vit_finders;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ImageView;
        import android.widget.ListView;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

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

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("items");
//        mStorageReference =  FirebaseStorage.getInstance().getReference();

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            private static final String TAG = "LostItemsActivity";

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ListItem listItem = snapshot.getValue(ListItem.class);
                itemList.add(listItem);
//                Log.w(TAG, itemList);
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
                    intent.putExtra("imageUrl", listItem.getImageUrl());
                    startActivity(intent);
                } catch (Exception e) {
                    Log.w(TAG, e.getMessage());
                }
//                String key = mDatabaseReference.child("items").getKey();
//                intent.putExtra("listItem", itemList);
////                intent.putExtra("key", key);
////                startActivity(intent);
//                Log.w(TAG, "key = " + key);
            }
        });

        // Setup FAB to open AddItemActivity
//        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LostItemsActivity.this, AddItemActivity.class);
//                startActivity(intent);
//            }
//        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search, menu);
//        MenuItem item = menu.findItem(R.id.search);
//        SearchView searchView = (SearchView) item.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    private void textSearch(String str){
//
//    }
}
