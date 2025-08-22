package co.za.carrental.factory;

import co.za.carrental.domain.Promotion;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PromotionFactoryTest {

    @Test
    void buildPromotion_shouldCreatePromotionWithGivenValues() {
        String code = "SUMMER25";
        float discount = 25.0f;
        Date expiryDate = new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000); // 7 days from now

        Promotion promo = PromotionFactory.buildPromotion(code, discount, expiryDate);

        assertNotNull(promo);
        assertNotNull(promo.getPromoId());
        assertTrue(promo.getPromoId().startsWith("PROMO-"));
        assertEquals(code, promo.getCode());
        assertEquals(discount, promo.getDiscount());
        assertEquals(expiryDate, promo.getExpiryDate());
    }

    @Test
    void buildPromotion_shouldThrowExceptionForNullCode() {
        float discount = 10.0f;
        Date expiryDate = new Date(System.currentTimeMillis() + 86400000);

        assertThrows(IllegalArgumentException.class, () ->
                PromotionFactory.buildPromotion(null, discount, expiryDate)
        );
    }

    @Test
    void buildPromotion_shouldThrowExceptionForNonPositiveDiscount() {
        String code = "ZERO";
        Date expiryDate = new Date(System.currentTimeMillis() + 86400000);

        assertThrows(IllegalArgumentException.class, () ->
                PromotionFactory.buildPromotion(code, 0.0f, expiryDate)
        );

        assertThrows(IllegalArgumentException.class, () ->
                PromotionFactory.buildPromotion(code, -5.0f, expiryDate)
        );
    }

    @Test
    void buildPromotion_shouldThrowExceptionForPastExpiryDate() {
        String code = "PAST";
        float discount = 5.0f;
        Date expiryDate = new Date(System.currentTimeMillis() - 86400000); // yesterday

        assertThrows(IllegalArgumentException.class, () ->
                PromotionFactory.buildPromotion(code, discount, expiryDate)
        );
    }
}
