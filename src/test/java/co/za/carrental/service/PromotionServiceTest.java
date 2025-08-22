package co.za.carrental.service;

import co.za.carrental.domain.Promotion;
import co.za.carrental.factory.PromotionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PromotionServiceTest {

    @Autowired
    private IPromotionService promotionService;

    @Test
    void saveAndFindById_shouldPersistAndRetrievePromotion() {
        String code = "SUMMER25";
        float discount = 25.0f;
        Date expiryDate = new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000); // 7 days from now

        Promotion promo = PromotionFactory.buildPromotion(code, discount, expiryDate);
        Promotion saved = promotionService.save(promo);

        assertNotNull(saved);
        assertNotNull(saved.getPromoId());

        Optional<Promotion> found = promotionService.findById(saved.getPromoId());
        assertTrue(found.isPresent());
        assertEquals(saved.getPromoId(), found.get().getPromoId());
        assertEquals(code, found.get().getCode());
        assertEquals(discount, found.get().getDiscount());
        assertEquals(expiryDate, found.get().getExpiryDate());
    }

    @Test
    void update_shouldModifyPromotion() {
        String code = "WINTER10";
        float discount = 10.0f;
        Date expiryDate = new Date(System.currentTimeMillis() + 5L * 24 * 60 * 60 * 1000); // 5 days

        Promotion promo = PromotionFactory.buildPromotion(code, discount, expiryDate);
        Promotion saved = promotionService.save(promo);

        assertNotNull(saved);
        assertNotNull(saved.getPromoId());

        // Update discount and code
        saved.setDiscount(15.0f);
        saved.setCode("WINTER15");

        Promotion updated = promotionService.update(saved);
        assertNotNull(updated);
        assertEquals(saved.getPromoId(), updated.getPromoId());
        assertEquals(15.0f, updated.getDiscount());
        assertEquals("WINTER15", updated.getCode());
    }

    @Test
    void deleteById_shouldRemovePromotion() {
        String code = "FLASH50";
        float discount = 50.0f;
        Date expiryDate = new Date(System.currentTimeMillis() + 2L * 24 * 60 * 60 * 1000); // 2 days

        Promotion promo = PromotionFactory.buildPromotion(code, discount, expiryDate);
        Promotion saved = promotionService.save(promo);

        assertNotNull(saved);
        assertNotNull(saved.getPromoId());

        promotionService.deleteById(saved.getPromoId());

        Optional<Promotion> found = promotionService.findById(saved.getPromoId());
        assertFalse(found.isPresent());
    }
}
