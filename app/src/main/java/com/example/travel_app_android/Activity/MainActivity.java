package com.example.travel_app_android.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.example.travel_app_android.Adapter.SliderAdapter;
import com.example.travel_app_android.Domain.Location;
import com.example.travel_app_android.Domain.SliderItems;
import com.example.travel_app_android.R;
import com.example.travel_app_android.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseDatabase database;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // Initialize Firebase with explicit database URL
        database = FirebaseDatabase.getInstance("https://travel-app-android-73928-default-rtdb.asia-southeast1.firebasedatabase.app/");
        
        initLocations();
        initBanners();
    }

    private void initLocations() {
        DatabaseReference myref = database.getReference("Location");
        ArrayList<Location> list = new ArrayList<>();
        
        Log.d(TAG, "Starting to load locations from Firebase...");
        
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "Firebase data received. Snapshot exists: " + snapshot.exists());
                
                if(snapshot.exists()) {
                    Log.d(TAG, "Processing " + snapshot.getChildrenCount() + " location items");
                    
                    for(DataSnapshot issuee : snapshot.getChildren()) {
                        Location location = issuee.getValue(Location.class);
                        if(location != null) {
                            list.add(location);
                            Log.d(TAG, "Added location: " + location.toString() + " (ID: " + location.getId() + ")");
                        } else {
                            Log.w(TAG, "Failed to parse location data from snapshot");
                        }
                    }

                    ArrayAdapter<Location> adapter = new ArrayAdapter<>(MainActivity.this,
                            R.layout.sp_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.locationSp.setAdapter(adapter);
                    
                    Log.d(TAG, "Location spinner adapter set with " + list.size() + " items");
                } else {
                    Log.w(TAG, "No location data found in Firebase");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Firebase database error: " + error.getMessage());
                Log.e(TAG, "Error code: " + error.getCode());
                Log.e(TAG, "Error details: " + error.getDetails());
            }
        });
    }

    private void banners(ArrayList<SliderItems> items) {
        binding.viewPager2.setAdapter(new SliderAdapter(items,binding.viewPager2));
        binding.viewPager2.setClipToPadding(false);
        binding.viewPager2.setClipChildren(false);
        binding.viewPager2.setOffscreenPageLimit(3);
        binding.viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        binding.viewPager2.setPageTransformer(compositePageTransformer);
    }

    private void initBanners() {
        DatabaseReference myRef = database.getReference("Banner");
        binding.progressBarBanner.setVisibility(View.VISIBLE);
        ArrayList<SliderItems> items = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        items.add(issue.getValue(SliderItems.class));
                    }
                    banners(items);
                    binding.progressBarBanner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}