package co.za.carrental.service;

import co.za.carrental.domain.Customer;
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

    public Customer create(Customer customer) {
        return repository.save(customer);
    }

    public Optional<Customer> read(String customerId) {
        return repository.findById(customerId);
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    public List<Customer> getAll() {
        return repository.findAll();
    }

    public void delete(String customerId) {
        repository.deleteById(customerId);
    }
}
