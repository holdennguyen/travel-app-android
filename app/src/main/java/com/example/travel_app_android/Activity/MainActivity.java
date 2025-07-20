package com.example.travel_app_android.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.travel_app_android.Domain.Location;
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
}