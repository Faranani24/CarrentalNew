package co.za.carrental.domain; // Make sure this package matches your domain entities

public enum BookingStatus {
    PENDING,        // Booking has been made but not yet confirmed or paid
    CONFIRMED,      // Booking is confirmed, often after payment or initial approval
    ACTIVE,         // The rental period has started
    COMPLETED,      // The rental period has ended, vehicle returned
    CANCELLED,      // Booking was cancelled by customer or admin
    REJECTED        // Booking request was denied (e.g., vehicle unavailable)
}
