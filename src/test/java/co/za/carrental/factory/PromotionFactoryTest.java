package co.za.carrental.factory;

import co.za.carrental.domain.Promotion;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PromotionFactoryTest.java
 * Branch entity class for car rental system
 * Author: Milani Ncana (216269369)
 *
 */

public class PromotionFactoryTest {
    @Test
    public void testCreatePromotion_success() {
        // Given
        String code = "WINTER25";
        float discount = 25.0f;
        Date expiryDate = new Date(); // Now (can be changed to future)

        // When
        Promotion promo = PromotionFactory.createPromotion(code, discount, expiryDate);

        // Then
        assertNotNull(promo);
        assertNotNull(promo.getPromoId());
        assertEquals(code, promo.getCode());
        assertEquals(discount, promo.getDiscount());
        assertEquals(expiryDate, promo.getExpiryDate());

        System.out.println("✅ Promotion created: " + promo);
    }

    @Test
    public void testCreatePromotion_withInvalidCode_shouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                PromotionFactory.createPromotion("", 15.0f, new Date()));

        assertEquals("Promo code is required.", exception.getMessage());
    }

    @Test
    public void testCreatePromotion_withInvalidDiscount_shouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                PromotionFactory.createPromotion("SUMMER50", 0.0f, new Date()));

        assertEquals("Discount must be greater than 0.", exception.getMessage());
    }

    @Test
    public void testCreatePromotion_withNullExpiry_shouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                PromotionFactory.createPromotion("SPRING15", 15.0f, null));

        assertEquals("Expiry date is required.", exception.getMessage());
    }
}
