package co.za.carrental.repository;

import co.za.carrental.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    Optional<Booking> findByCustomer_CustomerId(String customerId);

}

