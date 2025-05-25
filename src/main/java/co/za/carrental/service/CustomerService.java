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

    // Implements save from ICustomerService, covers both create and update
    @Override
    public Customer save(Customer customer) {
        // For 'create', it saves a new customer.
        // For 'update', if a customer with the ID exists, it updates it.
        return repository.save(customer);
    }

    // Corrected method: Returns Optional<Customer> as repository.findById() does.
    // This implements findById from ICustomerService.
    @Override
    public Optional<Customer> findById(String customerId) {
        return repository.findById(customerId);
    }

    // Implements findAll from ICustomerService
    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    // Implements deleteById from ICustomerService
    @Override
    public void deleteById(String customerId) {
        repository.deleteById(customerId);
    }

    // --- Optional: Specific update method if you need custom logic ---
    // If your update logic is more complex than a simple save
    public Customer update(Customer customer) {
        if (customer.getCustomerId() == null || !repository.existsById(customer.getCustomerId())) {
            // Or throw an exception like new NoSuchElementException("Customer not found for update");
            throw new IllegalArgumentException("Cannot update customer: ID is null or customer does not exist.");
        }
        return repository.save(customer); // Saves changes to existing customer
    }
}