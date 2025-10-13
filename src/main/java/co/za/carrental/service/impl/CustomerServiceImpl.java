package co.za.carrental.service.impl;

import co.za.carrental.domain.Customer;
import co.za.carrental.repository.CustomerRepository;
import co.za.carrental.service.ICustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository repo;

    public CustomerServiceImpl(CustomerRepository repo) {
        this.repo = repo;
    }

    @Override
    public Customer save(Customer customer) {
        return repo.save(customer);
    }

    @Override
    public Optional<Customer> findById(String customerId) {
        return repo.findById(customerId);
    }

    @Override
    public Customer update(Customer customer) {
        return repo.save(customer);
    }

    @Override
    public void deleteById(String customerId) {
        repo.deleteById(customerId);
    }

    @Override
    public List<Customer> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }

    // NEW: Find customer by email and password (for login)
    @Override
    public Optional<Customer> findByEmailAndPassword(String email, String password) {
        return Optional.ofNullable(repo.findByEmailAndPassword(email, password));
    }

    @Override
    public boolean emailExist(String email) {
        return false;
    }

    @Override
    public boolean emailExists(String email) {
        return false;
    }
}
