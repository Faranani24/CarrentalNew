package co.za.carrental.service;

import co.za.carrental.domain.Customer;
import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    Customer save(Customer customer);
    Optional<Customer> findById(String customerId);
    Customer update(Customer customer);
    void deleteById(String customerId);
    List<Customer> getAll();

    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Customer> findByEmailAndPassword(String email, String password);
    boolean emailExist(String email);

    boolean emailExists(String email);
}