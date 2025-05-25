package co.za.carrental.repository;

import co.za.carrental.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, String> {

    // THIS IS THE CRUCIAL CHANGE
    // Use 'customer_customerId' to specify the path to the ID within the Customer entity
    Optional<Booking> findByCustomer_CustomerId(String customerId);

    // Assuming you still have this from before (if not, add it back)
    Optional<Booking> findByCarId(String carId);
}