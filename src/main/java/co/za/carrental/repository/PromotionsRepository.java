package co.za.carrental.repository;

import co.za.carrental.domain.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PromotionsRepository extends JpaRepository<Promotion, String> {

    // Find by code (exact match)
    Promotion findByCode(String code);

    // Find promotions that contain a keyword in the code
    List<Promotion> findByCodeContainingIgnoreCase(String code);

    // Find promotions with a discount greater than a specific value
    List<Promotion> findByDiscountGreaterThan(float discount);

    // Find expired promotions
    List<Promotion> findByExpiryDateBefore(Date currentDate);

    // Find non-expired promotions
    List<Promotion> findByExpiryDateAfter(Date currentDate);

    // Find top 3 biggest discounts
    List<Promotion> findTop3ByOrderByDiscountDesc();

    // Using JPQL: promotions expiring between two dates
    @Query("SELECT p FROM Promotion p WHERE p.expiryDate BETWEEN :start AND :end")
    List<Promotion> findPromotionsExpiringBetween(@Param("start") Date start, @Param("end") Date end);
}
