package co.za.carrental.repository;

import co.za.carrental.domain.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CarTypeRepository extends JpaRepository<CarType, String> {

    Optional<CarType> findBySeatingCapacity(int seatingCapacity);

    List<CarType> findByDailyRateLessThanEqual(Float rate);

    List<CarType> findByDailyRateGreaterThanEqual(Float rate);

    List<CarType> findByLateFeePerHourGreaterThan(Float lateFee);

    List<CarType> findByLateFeePerHourLessThan(Float lateFee);
}

