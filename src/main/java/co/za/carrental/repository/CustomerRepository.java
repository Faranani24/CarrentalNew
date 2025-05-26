package co.za.carrental.repository;

import co.za.carrental.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {


    Optional<Customer> findByPhoneNumber(String phoneNumber);


}