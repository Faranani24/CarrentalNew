package co.za.carrental.service;

import co.za.carrental.domain.Customer; // Only Customer is needed for core CustomerService operations
import co.za.carrental.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository repository;

    @Autowired
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }


    @Override
    public Customer save(Customer customer) {
        return repository.save(customer);
    }


    @Override
    public Optional<Customer> findById(String customerId) {
        return repository.findById(customerId);
    }


    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }


    @Override
    public void deleteById(String customerId) {
        repository.deleteById(customerId);
    }


    public Customer update(Customer customer) {
        if (customer.getCustomerId() == null || !repository.existsById(customer.getCustomerId())) {
            throw new IllegalArgumentException("Cannot update customer: ID is null or customer does not exist.");
        }
        return repository.save(customer);
    }
}