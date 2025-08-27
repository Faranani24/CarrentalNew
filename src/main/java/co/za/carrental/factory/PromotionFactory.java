/*
 *
 * Author: Milani Ncana (216269369)
 * Date: 11 May 2025
 */

package co.za.carrental.factory;

import co.za.carrental.domain.Promotion;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

/*
 * Factory for creating Promotion domain objects. Validates required fields and
 * generates a UUID-based promoId.
 */
public class PromotionFactory {


    public static Promotion buildPromotion(String code, float discount, Date expiryDate) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Promo code is required.");
        }
        if (discount <= 0.0f) {
            throw new IllegalArgumentException("Discount must be greater than 0.");
        }
        if (expiryDate == null) {
            throw new IllegalArgumentException("Expiry date is required.");
        }

        // Optional: ensure expiry is not in the past
        Date now = new Date();
        if (expiryDate.before(now)) {
            throw new IllegalArgumentException("Expiry date must be in the future.");
        }

        String promoId = "PROMO-" + UUID.randomUUID().toString();

        return new Promotion.Builder()
                .setPromoId(promoId)
                .setCode(code.trim())
                .setDiscount(discount)
                .setExpiryDate(expiryDate)
                .build();
    }

    public static Promotion buildPromotion(String code, float discount) {
        throw new UnsupportedOperationException(
                "This method is deprecated for persistence. Use buildPromotion(code, discount, expiryDate) instead.");
    }

    //Create createPromotions() method to fix error in Controller test


}
