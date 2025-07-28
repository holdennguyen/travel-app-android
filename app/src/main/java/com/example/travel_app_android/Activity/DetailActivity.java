package com.example.travel_app_android.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.travel_app_android.Domain.BookingOrder;
import com.example.travel_app_android.Domain.Item;
import com.example.travel_app_android.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    private Item object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        setVariable();

    }

    private void setVariable() {
        binding.titleTxt.setText(object.getTitle());
        binding.priceTxt.setText("$" + object.getPrice());
        binding.backBtn.setOnClickListener(v -> finish());
        binding.bedTxt.setText("" + object.getBed());
        binding.durationTxt.setText(object.getDuration());
        binding.distanceTxt.setText(object.getDistance());
        binding.descriptionTxt.setText(object.getDescription());
        binding.addressTxt.setText(object.getAddress());
        binding.ratingBar.setRating((float) object.getScore());
        binding.ratingTxt.setText(object.getScore() + " Rating");

        Glide.with(DetailActivity.this)
                .load(object.getPic())
                .into(binding.pic);

        binding.addToCartBtn.setOnClickListener(view -> {
            createBookingForItem();
        });

    }

    private void createBookingForItem() {
        Log.d("DetailActivity", "🛒 Add to Cart clicked for: " + object.getTitle());
        
        // Generate unique order ID
        String orderId = "ORDER_" + System.currentTimeMillis();
        
        // Demo data for booking (in real app, this would come from user input)
        String userId = "demo_user_001";
        String userName = "Nguyễn Văn A";
        String userEmail = "nguyen.vana@email.com";
        String userPhone = "+84 123 456 789";
        String tourDate = "25/12/2024";
        String tourTime = "08:00 AM";
        int numberOfGuests = 2;
        int totalPrice = object.getPrice() * numberOfGuests;
        
        Log.d("DetailActivity", "💰 Creating booking: " + orderId + " for " + object.getTitle() + " - Price: $" + totalPrice);
        
        // Create booking order
        BookingOrder booking = new BookingOrder(
                orderId, userId, userName, userEmail, userPhone,
                object, tourDate, tourTime, numberOfGuests, totalPrice
        );
        
        // Save to Firebase
        ProfileActivity.createBooking(booking);
        
        // Show success message
        Toast.makeText(this, "Đã thêm tour vào giỏ! Kiểm tra trong hồ sơ của bạn.", Toast.LENGTH_LONG).show();
        
        // Navigate to profile to see the booking
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void getIntentExtra() {
        object = (Item) getIntent().getSerializableExtra("object");
    }
}