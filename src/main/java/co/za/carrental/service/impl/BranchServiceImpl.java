package co.za.carrental.service.impl;

import co.za.carrental.domain.Branch;
import co.za.carrental.repository.BranchRepository;
import co.za.carrental.service.IBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchServiceImpl implements IBranchService {

    private final BranchRepository branchRepository;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository) {
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
        return branchRepository.save(branch);
    }

    @Override
    public void deleteById(String branchId) {
        branchRepository.deleteById(branchId);
    }

    // convenience aliases (to match BookingServiceImpl pattern)
    @Override
    public Branch create(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    public Optional<Branch> read(String branchId) {
        return branchRepository.findById(branchId);
    }

    @Override
    public List<Branch> getAll() {
        return branchRepository.findAll();
    }

    @Override
    public void delete(String branchId) {
        branchRepository.deleteById(branchId);
    }

    @Override
    public void customBranchLogic() {
        // Add branch-specific business rules here if needed.
    }

    @Override
    public List<Branch> findByAddressContaining(String keyword) {
        return branchRepository.findByAddressContainingIgnoreCase(keyword);
    }

    @Override
    public List<Branch> findByPhone(String phone) {
        return branchRepository.findByPhone(phone);
    }
}
