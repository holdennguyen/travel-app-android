package com.example.travel_app_android.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.travel_app_android.Domain.BookingOrder;
import com.example.travel_app_android.Domain.Item;
import com.example.travel_app_android.databinding.ActivityTicketBinding;

public class TicketActivity extends AppCompatActivity {
    ActivityTicketBinding binding;
    private Item object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityTicketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        setVariable();
    }

    private void setVariable() {

        Glide.with(TicketActivity.this)
                .load(object.getPic())
                .into(binding.pic);

        Glide.with(TicketActivity.this)
                .load(object.getTourGuidePic())
                .into(binding.profile);

        binding.backBtn.setOnClickListener(v -> finish());
        binding.titleTxt.setText(object.getTitle());
        binding.durationTxt.setText(object.getDuration());
        binding.tourGuideNameTxt.setText(object.getTourGuideName());
        binding.timeTxt.setText(object.getTimeTour());
        binding.tourguideTxt.setText(object.getDateTour());

        binding.callBtn.setOnClickListener(view -> {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("sms:" + object.getTourGuidePhone()));
            sendIntent.putExtra("sms_body", "type your message");
            startActivity(sendIntent);
        });

        binding.messageBtn.setOnClickListener(view -> {
            String phone = object.getTourGuidePhone();
            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.fromParts("tel", phone, null));
            startActivity(intent);
        });

        // Handle download ticket button - go to profile to manage booking
        binding.button2.setOnClickListener(view -> {
            Toast.makeText(this, "Chuyển đến hồ sơ để quản lý đặt chỗ của bạn", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    private void createBookingForItem() {
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
        
        // Create booking order
        BookingOrder booking = new BookingOrder(
                orderId, userId, userName, userEmail, userPhone,
                object, tourDate, tourTime, numberOfGuests, totalPrice
        );
        
        // Save to Firebase
        ProfileActivity.createBooking(booking);
        
        // Show success message
        Toast.makeText(this, "Đặt tour thành công! Kiểm tra trong hồ sơ của bạn.", Toast.LENGTH_LONG).show();
        
        // Navigate to profile to see the booking
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void getIntentExtra() {
        object = (Item) getIntent().getSerializableExtra("object");
    }
}