package co.za.carrental.repository;

import co.za.carrental.domain.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {

    // Find promotion by exact code
    Optional<Promotion> findByCode(String code);

    // Find promotions with discount greater than the given value
    List<Promotion> findByDiscountGreaterThan(float discount);

    // Find expired promotions (expiryDate before given date)
    List<Promotion> findByExpiryDateBefore(Date date);

    // Find promotions that are still valid (expiryDate after given date)
    List<Promotion> findByExpiryDateAfter(Date date);

    // Top 3 promotions ordered by discount descending
    List<Promotion> findTop3ByOrderByDiscountDesc();
}
