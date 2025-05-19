package co.za.carrental.factory;

import java.util.Date;
import java.util.UUID;

import co.za.carrental.domain.Promotion;

/**
 * PromotionFactory.java
 * Branch entity class for car rental system
 * Author: Milani Ncana (216269369)
 *
 */

public class PromotionFactory {
    public static Promotion createPromotion(String code, float discount, Date expiryDate) {
        // Generate a unique promo ID (e.g., UUID or something shorter like "PROMO-001")
        String promoId = "PROMO-" + UUID.randomUUID();

        // Simple validation (optional)
        if (code == null || code.isEmpty()) throw new IllegalArgumentException("Promo code is required.");
        if (discount <= 0) throw new IllegalArgumentException("Discount must be greater than 0.");
        if (expiryDate == null) throw new IllegalArgumentException("Expiry date is required.");

        return new Promotion.Builder()
                .promoId(promoId)
                .code(code)
                .discount(discount)
                .expiryDate(expiryDate)
                .build();
    }
    
}
