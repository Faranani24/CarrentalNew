package co.za.carrental.repository;

import co.za.carrental.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, String> {

    // Custom: Find branches by keyword in their address (case-insensitive)
    List<Branch> findByAddressContainingIgnoreCase(String keyword);
}

