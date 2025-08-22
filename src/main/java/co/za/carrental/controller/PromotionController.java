/*
 *
 * Author: Milani Ncana (216269369)
 * Date: 11 May 2025
 */

package co.za.carrental.controller;

import co.za.carrental.domain.Promotion;
import co.za.carrental.service.IPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    private final IPromotionService promotionService;

    @Autowired
    public PromotionController(IPromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @PostMapping
    public ResponseEntity<Promotion> create(@RequestBody Promotion promotion) {
        Promotion created = promotionService.create(promotion);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotion> read(@PathVariable String id) {
        Optional<Promotion> promo = promotionService.read(id);
        return promo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promotion> update(@PathVariable String id, @RequestBody Promotion promotion) {
        promotion.setPromoId(id);
        Promotion updated = promotionService.update(promotion);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        promotionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Promotion>> getAll() {
        List<Promotion> promotions = promotionService.getAll();
        return ResponseEntity.ok(promotions);
    }

    /**
     * Search endpoint examples:
     * - ?code=CODE returns promotion by code
     * - ?minDiscount=10 returns promotions with discount > minDiscount
     * - ?valid=true returns promotions whose expiryDate is after now
     *
     * If no params provided, returns all promotions.
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) Float minDiscount,
            @RequestParam(required = false) Boolean valid) {

        if (code != null && !code.trim().isEmpty()) {
            Optional<Promotion> promo = promotionService.findByCode(code);
            return promo.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        if (minDiscount != null) {
            List<Promotion> promos = promotionService.findByDiscountGreaterThan(minDiscount);
            return ResponseEntity.ok(promos);
        }

        if (Boolean.TRUE.equals(valid)) {
            Date now = new Date();
            List<Promotion> promos = promotionService.findValidPromotions(now);
            return ResponseEntity.ok(promos);
        }

        // fallback to all
        return ResponseEntity.ok(promotionService.getAll());
    }
}
