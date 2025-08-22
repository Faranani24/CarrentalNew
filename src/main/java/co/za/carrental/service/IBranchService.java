package co.za.carrental.service;

import co.za.carrental.domain.Branch;

import java.util.List;
import java.util.Optional;

public interface IBranchService extends IService<Branch, String> {

    // Custom business logic placeholder
    void customBranchLogic();

    // CRUD
    Branch save(Branch branch);

    Optional<Branch> findById(String branchId);

    List<Branch> findAll();

    void deleteById(String branchId);

    // Useful queries
    List<Branch> findByAddressContaining(String keyword);

    List<Branch> findByPhone(String phone);
}