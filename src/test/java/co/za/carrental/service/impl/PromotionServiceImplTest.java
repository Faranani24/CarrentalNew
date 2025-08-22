/*
 *
 * Author: Milani Ncana (216269369)
 * Date: 11 May 2025
 */

package co.za.carrental.service.impl;

import co.za.carrental.domain.Promotion;
import co.za.carrental.factory.PromotionFactory;
import co.za.carrental.service.IPromotionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PromotionServiceImplTest {

    @Autowired
    private IPromotionService promotionService;

    private Promotion testPromotion;

    @BeforeEach
    void setUp() {
        String code = "SUMMER25";
        Float discount = 25.0f;
        Date expiryDate = new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000); // 7 days from now

        testPromotion = PromotionFactory.buildPromotion(code, discount, expiryDate);
        testPromotion = promotionService.save(testPromotion);

        assertNotNull(testPromotion);
        assertNotNull(testPromotion.getPromoId());
    }

    @Test
    void read_shouldReturnPromotionById() {
        Optional<Promotion> found = promotionService.findById(testPromotion.getPromoId());
        assertTrue(found.isPresent());
        assertEquals(testPromotion.getPromoId(), found.get().getPromoId());
    }

    @Test
    void create_shouldSavePromotion() {
        Promotion newPromo = PromotionFactory.buildPromotion(
                "FLASH50",
                50.0f,
                new Date(System.currentTimeMillis() + 2L * 24 * 60 * 60 * 1000) // 2 days from now
        );
        Promotion saved = promotionService.save(newPromo);
        assertNotNull(saved);
        assertNotNull(saved.getPromoId());
    }

    @Test
    void update_shouldUpdatePromotion() {
        testPromotion.setDiscount(30.0f);
        testPromotion.setCode("SUMMER30");
        Promotion updated = promotionService.update(testPromotion);
        assertNotNull(updated);
        assertEquals(testPromotion.getPromoId(), updated.getPromoId());
        assertEquals(30.0f, updated.getDiscount());
        assertEquals("SUMMER30", updated.getCode());
    }

    @Test
    void delete_shouldRemovePromotionById() {
        promotionService.deleteById(testPromotion.getPromoId());
        Optional<Promotion> found = promotionService.findById(testPromotion.getPromoId());
        assertFalse(found.isPresent());
    }
}
