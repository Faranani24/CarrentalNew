package co.za.carrental.service;

import co.za.carrental.domain.Branch;
import co.za.carrental.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class BranchService implements IBranchService {

    private final BranchRepository branchRepository;

    @Autowired
    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public Branch save(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    public Optional<Branch> findById(String branchId) {
        return branchRepository.findById(branchId);
    }

    @Override
    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

    @Override
    public Branch update(Branch branch) {
        if (branchRepository.existsById(branch.getBranchId())) {
            return branchRepository.save(branch);
        }
        throw new IllegalArgumentException("Branch with ID " + branch.getBranchId() + " does not exist.");
    }

    @Override
    public void deleteById(String branchId) {
        branchRepository.deleteById(branchId);
    }

    @Override
    public List<Branch> findByAddressContaining(String keyword) {
        return branchRepository.findByAddressContainingIgnoreCase(keyword);
    }

}
