package co.za.carrental.service;

import co.za.carrental.domain.Branch;

import java.util.List;
import java.util.Optional;

public interface IBranchService {

    // Create or update a branch
    Branch save(Branch branch);

    // Get a branch by its ID
    Optional<Branch> findById(String branchId);

    // Get all branches
    List<Branch> findAll();

    // Update a branch
    Branch update(Branch branch);

    // Delete a branch by ID
    void deleteById(String branchId);

    // Custom: Search branches by city or keyword in address
    List<Branch> findByAddressContaining(String keyword);

}
