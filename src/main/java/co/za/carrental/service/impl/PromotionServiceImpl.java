/*
 *
 * Author: Milani Ncana (216269369)
 * Date: 11 May 2025
 */

package co.za.carrental.service.impl;

import co.za.carrental.domain.Promotion;
import co.za.carrental.repository.PromotionRepository;
import co.za.carrental.service.IPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements IPromotionService {

    private final PromotionRepository promotionRepository;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public Promotion save(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    public Optional<Promotion> findById(String promoId) {
        return promotionRepository.findById(promoId);
    }

    @Override
    public List<Promotion> findAll() {
        return promotionRepository.findAll();
    }

    @Override
    public Promotion update(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    public void deleteById(String promoId) {
        promotionRepository.deleteById(promoId);
    }

    // convenience aliases (to match BookingServiceImpl pattern)
    @Override
    public Promotion create(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    public Optional<Promotion> read(String promoId) {
        return promotionRepository.findById(promoId);
    }

    @Override
    public List<Promotion> getAll() {
        return promotionRepository.findAll();
    }

    @Override
    public void delete(String promoId) {
        promotionRepository.deleteById(promoId);
    }

    @Override
    public void customPromotionLogic() {
        // Add domain-specific promotion logic here if needed here.
    }

    @Override
    public Optional<Promotion> findByCode(String code) {
        return promotionRepository.findByCode(code);
    }

    @Override
    public List<Promotion> findByDiscountGreaterThan(float discount) {
        return promotionRepository.findByDiscountGreaterThan(discount);
    }

    @Override
    public List<Promotion> findValidPromotions(Date currentDate) {
        return promotionRepository.findByExpiryDateAfter(currentDate);
    }
}
