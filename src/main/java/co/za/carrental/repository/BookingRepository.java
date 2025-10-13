package co.za.carrental.repository;

import co.za.carrental.domain.Booking;
import co.za.carrental.domain.BookingStatus;
import co.za.carrental.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    Optional<Booking> findByCustomer_CustomerId(String customerId);

    List<Booking> findByCustomer_CustomerIdAndStatus(String customerId, BookingStatus status);

    List<Booking> findByStatus(BookingStatus status);

    List<Booking> findByCar_CarId(String carId);

    List<Booking> findByCar_CarIdAndStatus(String carId, BookingStatus status);

    List<Booking> findByStartDateBetween(Date from, Date to);

    List<Booking> findByEndDateBetween(Date from, Date to);

    List<Booking> findByCar_CarIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            String carId, Date endDate, Date startDate
    );

    boolean existsByCar_CarIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            String carId, Date endDate, Date startDate
    );

    List<Booking> findByCar(Car car);
}
