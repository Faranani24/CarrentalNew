package co.za.carrental.repository;

import co.za.carrental.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByCarId(String carId);
    void deleteByCarId(String carId);

    // Fetch cars that are available between given dates
    @Query("SELECT c FROM Car c " +
            "WHERE c.status = 'AVAILABLE' " +
            "AND NOT EXISTS (" +
            "  SELECT b FROM Booking b " +
            "  WHERE b.car = c " +
            "    AND b.startDate <= :endDate " +
            "    AND b.endDate >= :startDate" +
            ")")
    List<Car> findAvailableCarsBetweenDates(@Param("startDate") String startDate,
                                            @Param("endDate") String endDate);
}
