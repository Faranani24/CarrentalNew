package co.za.carrental.repository;

import co.za.carrental.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    // Changed from findByPhoneNumber to findByPhone to match the 'phone' field in Customer entity
    Optional<Customer> findByPhone(String phoneNumber);
}
