package co.za.carrental.repository;

import co.za.carrental.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByEmail(String email);
    boolean existsByEmail(String email);
    Customer findByEmailAndPassword(String email, String password);
}