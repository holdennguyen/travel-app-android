package com.example.travel_app_android.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.travel_app_android.Adapter.BookingAdapter;
import com.example.travel_app_android.Domain.BookingOrder;
import com.example.travel_app_android.R;
import com.example.travel_app_android.databinding.ActivityProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProfileActivity extends AppCompatActivity implements BookingAdapter.OnBookingActionListener {
    private ActivityProfileBinding binding;
    private FirebaseDatabase database;
    private BookingAdapter bookingAdapter;
    private ArrayList<BookingOrder> bookingList;
    
    // Demo user data (can be replaced with real authentication)
    private static final String DEMO_USER_ID = "demo_user_001";
    private static final String DEMO_USER_NAME = "Nguyễn Văn A";
    private static final String DEMO_USER_EMAIL = "nguyen.vana@email.com";
    private static final String DEMO_USER_PHONE = "+84 123 456 789";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase
        database = FirebaseDatabase.getInstance("https://travel-app-android-73928-default-rtdb.asia-southeast1.firebasedatabase.app/");
        
        setupUI();
        setupRecyclerView();
        loadUserBookings();
    }

    private void setupUI() {
        // Set user information
        binding.userNameTxt.setText(DEMO_USER_NAME);
        binding.userEmailTxt.setText(DEMO_USER_EMAIL);
        binding.userPhoneTxt.setText(DEMO_USER_PHONE);
        
        // Handle back button
        binding.backBtn.setOnClickListener(v -> finish());
        
        // Handle add new booking button
        binding.addNewBookingBtn.setOnClickListener(v -> {
            // Navigate to MainActivity to browse tours
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
        });
        
        // Handle edit profile button (demo)
        binding.editProfileBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Chức năng chỉnh sửa hồ sơ sẽ được triển khai sau", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupRecyclerView() {
        bookingList = new ArrayList<>();
        bookingAdapter = new BookingAdapter(bookingList);
        bookingAdapter.setOnBookingActionListener(this);
        
        binding.recyclerViewBookings.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewBookings.setAdapter(bookingAdapter);
    }



    private void loadUserBookings() {
        binding.progressBar.setVisibility(View.VISIBLE);
        
        DatabaseReference bookingsRef = database.getReference("Bookings").child(DEMO_USER_ID);
        Log.d("ProfileActivity", "📖 Loading bookings from path: Bookings/" + DEMO_USER_ID);
        
        bookingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("ProfileActivity", "📊 Firebase data snapshot received. Exists: " + snapshot.exists());
                Log.d("ProfileActivity", "📊 Children count: " + snapshot.getChildrenCount());
                
                bookingList.clear();
                
                if (snapshot.exists()) {
                    for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                        BookingOrder booking = bookingSnapshot.getValue(BookingOrder.class);
                        if (booking != null) {
                            Log.d("ProfileActivity", "✅ Loaded booking: " + booking.getOrderId() + " - " + booking.getItemTitle());
                            bookingList.add(booking);
                        } else {
                            Log.w("ProfileActivity", "⚠️ Null booking found in snapshot: " + bookingSnapshot.getKey());
                        }
                    }
                    
                    Log.d("ProfileActivity", "📋 Total bookings loaded: " + bookingList.size());
                    
                    // Sort by timestamp (newest first)
                    Collections.sort(bookingList, new Comparator<BookingOrder>() {
                        @Override
                        public int compare(BookingOrder o1, BookingOrder o2) {
                            return Long.compare(o2.getTimestamp(), o1.getTimestamp());
                        }
                    });
                    
                    bookingAdapter.updateBookings(bookingList);
                    updateUI();
                } else {
                    // No bookings found
                    Log.d("ProfileActivity", "📭 No bookings found for user: " + DEMO_USER_ID);
                    showEmptyState();
                }
                
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ProfileActivity", "❌ Failed to load bookings: " + error.getMessage());
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ProfileActivity.this, "Lỗi tải dữ liệu: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                showEmptyState();
            }
        });
    }

    private void updateUI() {
        // Update total bookings count
        binding.totalBookingsTxt.setText(getString(R.string.total_bookings, bookingList.size()));
        
        // Show/hide content based on booking list
        if (bookingList.isEmpty()) {
            showEmptyState();
        } else {
            binding.recyclerViewBookings.setVisibility(View.VISIBLE);
            binding.emptyStateLayout.setVisibility(View.GONE);
        }
    }

    private void showEmptyState() {
        binding.recyclerViewBookings.setVisibility(View.GONE);
        binding.emptyStateLayout.setVisibility(View.VISIBLE);
        binding.totalBookingsTxt.setText(getString(R.string.total_bookings, 0));
    }

    // CRUD Operations Implementation

    @Override
    public void onEditBooking(BookingOrder booking, int position) {
        // Show edit booking dialog
        showEditBookingDialog(booking, position);
    }

    @Override
    public void onDeleteBooking(BookingOrder booking, int position) {
        // Delete booking from Firebase
        deleteBookingFromFirebase(booking, position);
    }



    private void showEditBookingDialog(BookingOrder booking, int position) {
        // Simple edit dialog - in real app, this would be a proper activity/fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chỉnh sửa đặt chỗ");
        
        // For demo, we'll just show available options
        String[] options = {"Thay đổi số khách", "Thay đổi ngày tour", "Cập nhật trạng thái thanh toán"};
        
        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0:
                    // Change number of guests (demo implementation)
                    int newGuestCount = booking.getNumberOfGuests() + 1;
                    booking.setNumberOfGuests(newGuestCount);
                    booking.setTotalPrice(booking.getTotalPrice() + 100); // Recalculate price
                    updateBookingInFirebase(booking, position);
                    break;
                case 1:
                    // Change tour date (demo implementation)
                    booking.setTourDate("25/12/2024");
                    updateBookingInFirebase(booking, position);
                    break;
                case 2:
                    // Update payment status
                    String newStatus = booking.getPaymentStatus().equals("Pending") ? "Paid" : "Pending";
                    booking.setPaymentStatus(newStatus);
                    updateBookingInFirebase(booking, position);
                    break;
            }
        });
        
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void updateBookingInFirebase(BookingOrder booking, int position) {
        DatabaseReference bookingRef = database.getReference("Bookings")
                .child(DEMO_USER_ID)
                .child(booking.getOrderId());
        
        bookingRef.setValue(booking)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, getString(R.string.booking_updated), Toast.LENGTH_SHORT).show();
                    bookingAdapter.updateBooking(position, booking);
                })
                .addOnFailureListener(e -> {
                    Log.e("ProfileActivity", "Failed to update booking: " + e.getMessage());
                    Toast.makeText(this, "Lỗi cập nhật: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void deleteBookingFromFirebase(BookingOrder booking, int position) {
        DatabaseReference bookingRef = database.getReference("Bookings")
                .child(DEMO_USER_ID)
                .child(booking.getOrderId());
        
        bookingRef.removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, getString(R.string.booking_deleted), Toast.LENGTH_SHORT).show();
                    bookingAdapter.removeBooking(position);
                    updateUI();
                })
                .addOnFailureListener(e -> {
                    Log.e("ProfileActivity", "Failed to delete booking: " + e.getMessage());
                    Toast.makeText(this, "Lỗi xóa: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }



    // Static method to create a new booking (called from TicketActivity)
    public static void createBooking(BookingOrder booking) {
        Log.d("ProfileActivity", "Creating booking with ID: " + booking.getOrderId());
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://travel-app-android-73928-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference bookingsRef = database.getReference("Bookings")
                .child(DEMO_USER_ID)
                .child(booking.getOrderId());
        
        Log.d("ProfileActivity", "Firebase path: Bookings/" + DEMO_USER_ID + "/" + booking.getOrderId());
        
        bookingsRef.setValue(booking)
                .addOnSuccessListener(aVoid -> {
                    Log.d("ProfileActivity", "✅ Booking created successfully: " + booking.getOrderId());
                    Log.d("ProfileActivity", "Booking details: " + booking.getItemTitle() + " - " + booking.getFormattedPrice());
                })
                .addOnFailureListener(e -> {
                    Log.e("ProfileActivity", "❌ Failed to create booking: " + e.getMessage());
                    e.printStackTrace();
                });
    }
} 