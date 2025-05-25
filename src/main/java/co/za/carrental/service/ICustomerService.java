package co.za.carrental.service;

import co.za.carrental.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    Customer save(Customer customer); // Can be used for create or update
    Optional<Customer> findById(String customerId);
    List<Customer> findAll();
    void deleteById(String customerId);
    // You might add specific update logic if different from save, e.g.,
    // Customer update(Customer customer);
}
