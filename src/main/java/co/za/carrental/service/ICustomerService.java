package co.za.carrental.service;

import co.za.carrental.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    Customer save(Customer customer);
    Optional<Customer> findById(String customerId);
    List<Customer> findAll();
    void deleteById(String customerId);

}
