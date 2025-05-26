package co.za.carrental.service;

import co.za.carrental.domain.Promotion;
import co.za.carrental.repository.PromotionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionsService implements IPromotionsService {

    private final PromotionsRepository repository;

    @Autowired
    public PromotionsService(PromotionsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Promotion save(Promotion promotion) {
        return repository.save(promotion);
    }

    @Override
    public Optional<Promotion> read(String promoId) {
        return repository.findById(promoId);
    }

    @Override
    public List<Promotion> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(String promoId) {
        repository.deleteById(promoId);
    }

    @Override
    public Optional<Promotion> findByCode(String code) {
        return Optional.ofNullable(repository.findByCode(code));
    }

    @Override
    public List<Promotion> findByDiscountGreaterThan(float discount) {
        return repository.findByDiscountGreaterThan(discount);
    }

    @Override
    public List<Promotion> findValidPromotions(Date currentDate) {
        return repository.findByExpiryDateAfter(currentDate);
    }

    @Override
    public List<Promotion> findExpiringPromotions(Date endDate) {
        return repository.findByExpiryDateBefore(endDate);
    }

    @Override
    public List<Promotion> findByCodePattern(String pattern) {
        return repository.findByCodeContainingIgnoreCase(pattern);
    }
}
