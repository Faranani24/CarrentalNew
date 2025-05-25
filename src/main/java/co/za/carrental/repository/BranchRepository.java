package co.za.carrental.repository;

import co.za.carrental.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, String> {

    // Find all branches by city (if your address includes city names)
    List<Branch> findByAddressContainingIgnoreCase(String city);

    // Find all branches by phone number (exact match)
    List<Branch> findByPhone(String phone);

    //Find branches starting with certain characters in address
    List<Branch> findByAddressStartingWithIgnoreCase(String prefix);

    // Sort branches by address (Spring Boot lets you do this with Sort, but here's a custom example)
    List<Branch> findAllByOrderByAddressAsc();



}
