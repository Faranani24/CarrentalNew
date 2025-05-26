package co.za.carrental.controller;

import co.za.carrental.domain.Branch;
import co.za.carrental.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class BranchController {

    @Autowired
    private BranchRepository branchRepository;

    @PostMapping
    public ResponseEntity<Branch> create(@RequestBody Branch branch) {
        Branch saved = branchRepository.save(branch);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Branch> getById(@PathVariable String id) {
        Branch found = branchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));
        return ResponseEntity.ok(found);
    }

    @GetMapping
    public ResponseEntity<List<Branch>> getAll() {
        return ResponseEntity.ok(branchRepository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Branch> update(@PathVariable String id, @RequestBody Branch branchDetails) {
        Branch existing = branchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

        Branch updated = new Branch.Builder()
                .branchId(existing.getBranchId())
                .address(branchDetails.getAddress())
                .phone(branchDetails.getPhone())
                .build();

        return ResponseEntity.ok(branchRepository.save(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found"));

        branchRepository.delete(branch);
        return ResponseEntity.noContent().build();
    }



}
