package com.example.travel_app_android.Domain;

import java.io.Serializable;

public class BookingOrder implements Serializable {
    private String orderId;
    private String userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    
    // Tour/Item details
    private String itemTitle;
    private String itemPic;
    private String itemAddress;
    private String duration;
    private String tourGuidePhone;
    private String tourGuideName;
    private String tourGuidePic;
    
    // Booking details
    private String bookingDate;    // When the booking was made
    private String tourDate;       // When the tour will take place
    private String tourTime;       // Tour time
    private int numberOfGuests;
    private int totalPrice;
    private String paymentStatus;  // "Paid", "Pending", "Cancelled"
    private String bookingStatus;  // "Confirmed", "Pending", "Cancelled", "Completed"
    
    // Additional info
    private String specialRequests;
    private long timestamp;        // For sorting/ordering

    public BookingOrder() {
        // Firebase requires empty constructor
    }

    public BookingOrder(String orderId, String userId, String userName, String userEmail, 
                       String userPhone, Item item, String tourDate, String tourTime, 
                       int numberOfGuests, int totalPrice) {
        this.orderId = orderId;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        
        // Copy item details
        this.itemTitle = item.getTitle();
        this.itemPic = item.getPic();
        this.itemAddress = item.getAddress();
        this.duration = item.getDuration();
        this.tourGuidePhone = item.getTourGuidePhone();
        this.tourGuideName = item.getTourGuideName();
        this.tourGuidePic = item.getTourGuidePic();
        
        this.tourDate = tourDate;
        this.tourTime = tourTime;
        this.numberOfGuests = numberOfGuests;
        this.totalPrice = totalPrice;
        this.bookingDate = getCurrentDate();
        this.paymentStatus = "Pending";
        this.bookingStatus = "Confirmed";
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserPhone() { return userPhone; }
    public void setUserPhone(String userPhone) { this.userPhone = userPhone; }

    public String getItemTitle() { return itemTitle; }
    public void setItemTitle(String itemTitle) { this.itemTitle = itemTitle; }

    public String getItemPic() { return itemPic; }
    public void setItemPic(String itemPic) { this.itemPic = itemPic; }

    public String getItemAddress() { return itemAddress; }
    public void setItemAddress(String itemAddress) { this.itemAddress = itemAddress; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getTourGuidePhone() { return tourGuidePhone; }
    public void setTourGuidePhone(String tourGuidePhone) { this.tourGuidePhone = tourGuidePhone; }

    public String getTourGuideName() { return tourGuideName; }
    public void setTourGuideName(String tourGuideName) { this.tourGuideName = tourGuideName; }

    public String getTourGuidePic() { return tourGuidePic; }
    public void setTourGuidePic(String tourGuidePic) { this.tourGuidePic = tourGuidePic; }

    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }

    public String getTourDate() { return tourDate; }
    public void setTourDate(String tourDate) { this.tourDate = tourDate; }

    public String getTourTime() { return tourTime; }
    public void setTourTime(String tourTime) { this.tourTime = tourTime; }

    public int getNumberOfGuests() { return numberOfGuests; }
    public void setNumberOfGuests(int numberOfGuests) { this.numberOfGuests = numberOfGuests; }

    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }

    public String getSpecialRequests() { return specialRequests; }
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    // Helper method to get current date
    private String getCurrentDate() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date());
    }

    // Utility methods
    public String getFormattedPrice() {
        return "$" + totalPrice;
    }

    public String getOrderSummary() {
        return itemTitle + " - " + numberOfGuests + " khách - " + tourDate;
    }
} 