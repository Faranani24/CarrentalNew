/*
 *
 * Author: Milani Ncana (216269369)
 * Date: 11 May 2025
 */

package co.za.carrental.controller;

import co.za.carrental.domain.Branch;
import co.za.carrental.service.IBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/branches")
public class BranchController {

    private final IBranchService branchService;

    @Autowired
    public BranchController(IBranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping
    public ResponseEntity<Branch> create(@RequestBody Branch branch) {
        Branch created = branchService.create(branch);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Branch> read(@PathVariable String id) {
        Optional<Branch> branch = branchService.read(id);
        return branch.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Branch> update(@PathVariable String id, @RequestBody Branch branch) {
        branch.setBranchId(id);
        Branch updated = branchService.update(branch);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        branchService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Branch>> getAll() {
        List<Branch> branches = branchService.getAll();
        return ResponseEntity.ok(branches);
    }

    /**
     * Search endpoint:
     * - ?address=keyword will search address-containing (case-insensitive)
     * - ?phone=number will search by phone
     * If both are omitted, returns all branches.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Branch>> search(
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String phone) {

        if (address != null && !address.trim().isEmpty()) {
            List<Branch> result = branchService.findByAddressContaining(address);
            return ResponseEntity.ok(result);
        }

        if (phone != null && !phone.trim().isEmpty()) {
            List<Branch> result = branchService.findByPhone(phone);
            return ResponseEntity.ok(result);
        }

        // fallback to all
        return ResponseEntity.ok(branchService.getAll());
    }
}
