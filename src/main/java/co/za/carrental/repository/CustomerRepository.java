package co.za.carrental.repository;

import co.za.carrental.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    /**
     * Find customer by email address
     * Used to check if customer already exists before creating a new one
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Check if customer exists by email
     */
    boolean existsByEmail(String email);
}