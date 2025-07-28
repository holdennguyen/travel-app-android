package com.example.travel_app_android.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travel_app_android.Domain.BookingOrder;
import com.example.travel_app_android.R;
import com.example.travel_app_android.databinding.ViewholderBookingBinding;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    private ArrayList<BookingOrder> bookingList;
    private Context context;
    private OnBookingActionListener listener;

    // Interface for handling booking actions
    public interface OnBookingActionListener {
        void onEditBooking(BookingOrder booking, int position);
        void onDeleteBooking(BookingOrder booking, int position);
        void onViewBookingDetails(BookingOrder booking);
    }

    public BookingAdapter(ArrayList<BookingOrder> bookingList) {
        this.bookingList = bookingList;
    }

    public void setOnBookingActionListener(OnBookingActionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderBookingBinding binding = ViewholderBookingBinding.inflate(
                LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingOrder booking = bookingList.get(position);
        
        // Bind data to views
        holder.binding.tourTitleTxt.setText(booking.getItemTitle());
        holder.binding.tourLocationTxt.setText(booking.getItemAddress());
        holder.binding.priceTxt.setText(booking.getFormattedPrice());
        holder.binding.guestsTxt.setText(context.getString(R.string.guests_count, booking.getNumberOfGuests()));
        holder.binding.orderIdTxt.setText("ID: " + booking.getOrderId());
        
        // Set booking date and tour date
        holder.binding.bookingDateTxt.setText(context.getString(R.string.booking_date, booking.getBookingDate()));
        holder.binding.tourDateTxt.setText(context.getString(R.string.tour_date, booking.getTourDate()));
        
        // Set booking status with appropriate background
        setBookingStatus(holder, booking.getBookingStatus());
        
        // Set payment status with appropriate background
        setPaymentStatus(holder, booking.getPaymentStatus());
        
        // Load tour image
        if (booking.getItemPic() != null && !booking.getItemPic().isEmpty()) {
            Glide.with(context)
                    .load(booking.getItemPic())
                    .into(holder.binding.tourImageView);
        }
        
        // Handle menu button click
        holder.binding.menuBtn.setOnClickListener(v -> showPopupMenu(v, booking, position));
        
        // Handle item click to view details
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onViewBookingDetails(booking);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    // Method to update booking list
    public void updateBookings(ArrayList<BookingOrder> newBookings) {
        this.bookingList = newBookings;
        notifyDataSetChanged();
    }

    // Method to add new booking
    public void addBooking(BookingOrder booking) {
        bookingList.add(0, booking); // Add to top
        notifyItemInserted(0);
    }

    // Method to remove booking
    public void removeBooking(int position) {
        if (position >= 0 && position < bookingList.size()) {
            bookingList.remove(position);
            notifyItemRemoved(position);
        }
    }

    // Method to update specific booking
    public void updateBooking(int position, BookingOrder updatedBooking) {
        if (position >= 0 && position < bookingList.size()) {
            bookingList.set(position, updatedBooking);
            notifyItemChanged(position);
        }
    }

    private void setBookingStatus(ViewHolder holder, String status) {
        int backgroundRes;
        int textColorRes = R.color.white;
        String statusText;

        switch (status) {
            case "Confirmed":
                backgroundRes = R.drawable.purple_bg;
                statusText = context.getString(R.string.booking_confirmed);
                break;
            case "Pending":
                backgroundRes = R.drawable.grey_bg;
                textColorRes = R.color.darkBlue;
                statusText = context.getString(R.string.booking_pending);
                break;
            case "Cancelled":
                backgroundRes = R.drawable.stroke_bg;
                textColorRes = R.color.darkBlue;
                statusText = context.getString(R.string.booking_cancelled);
                break;
            case "Completed":
                backgroundRes = R.drawable.light_purple_bg;
                textColorRes = R.color.darkBlue;
                statusText = context.getString(R.string.booking_completed);
                break;
            default:
                backgroundRes = R.drawable.grey_bg;
                textColorRes = R.color.darkBlue;
                statusText = status;
                break;
        }

        holder.binding.bookingStatusTxt.setBackgroundResource(backgroundRes);
        holder.binding.bookingStatusTxt.setTextColor(context.getResources().getColor(textColorRes, null));
        holder.binding.bookingStatusTxt.setText(statusText);
    }

    private void setPaymentStatus(ViewHolder holder, String status) {
        int backgroundRes;
        String statusText;

        switch (status) {
            case "Paid":
                backgroundRes = R.drawable.purple_bg;
                statusText = context.getString(R.string.payment_paid);
                break;
            case "Pending":
                backgroundRes = R.drawable.grey_bg;
                statusText = context.getString(R.string.payment_pending);
                break;
            case "Cancelled":
                backgroundRes = R.drawable.stroke_bg;
                statusText = context.getString(R.string.payment_cancelled);
                break;
            default:
                backgroundRes = R.drawable.grey_bg;
                statusText = status;
                break;
        }

        holder.binding.paymentStatusTxt.setBackgroundResource(backgroundRes);
        holder.binding.paymentStatusTxt.setText(statusText);
    }

    private void showPopupMenu(View view, BookingOrder booking, int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.booking_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_edit) {
                if (listener != null) {
                    listener.onEditBooking(booking, position);
                }
                return true;
            } else if (itemId == R.id.menu_delete) {
                showDeleteConfirmation(booking, position);
                return true;
            }
            return false;
        });

        popupMenu.show();
    }

    private void showDeleteConfirmation(BookingOrder booking, int position) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.confirm_delete))
                .setMessage(context.getString(R.string.delete_booking_message))
                .setPositiveButton(context.getString(R.string.yes), (dialog, which) -> {
                    if (listener != null) {
                        listener.onDeleteBooking(booking, position);
                    }
                })
                .setNegativeButton(context.getString(R.string.no), null)
                .show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderBookingBinding binding;

        public ViewHolder(ViewholderBookingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
} 