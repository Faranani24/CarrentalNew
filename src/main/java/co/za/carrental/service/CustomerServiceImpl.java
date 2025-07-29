package co.za.carrental.service.impl;

import co.za.carrental.domain.Customer;
import co.za.carrental.repository.CustomerRepository;
import co.za.carrental.service.ICustomerService;
import co.za.carrental.service.IService; // Make sure IService is imported if you're directly referring to its methods
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> read(String id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer update(Customer customer) {
        // As noted before, save() handles both insert and update if ID is managed.
        // You might want to add logic here to ensure the customer actually exists
        // before attempting an 'update' semantic, e.g., if (!customerRepository.existsById(customer.getCustomerId())) throw new NotFoundException();
        return customerRepository.save(customer);
    }

    // Your existing delete method (matches `delete(ID id)` from IService)
    @Override
    public void delete(String id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    // New methods to implement from IService:

    @Override
    public Customer save(Customer entity) {
        // Typically, 'save' is what Spring Data JPA repositories offer,
        // and you might map your 'create' or 'update' to this.
        // For simplicity, you can just call the repository's save.
        return customerRepository.save(entity);
    }

    @Override
    public Optional<Customer> findById(String id) {
        // This maps directly to your repository's findById
        return customerRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        // This maps directly to your repository's deleteById
        customerRepository.deleteById(id);
    }
}
