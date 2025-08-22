/*
 *
 * Author: Milani Ncana (216269369)
 * Date: 11 May 2025
 *
 */

package co.za.carrental.controller;

import co.za.carrental.domain.Branch;
import co.za.carrental.factory.BranchFactory;
import co.za.carrental.service.IBranchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BranchControllerTest {

    @Autowired
    private BranchController branchController;

    @Autowired
    private IBranchService branchService;

    private Branch testBranch;

    @BeforeEach
    void setUp() {
        testBranch = BranchFactory.createBranch(
                "B001", "Cape Town Central", "123 Main Street, Cape Town"
        );
        testBranch = branchService.create(testBranch);
    }

    @Test
    void create_shouldCreateBranch() {
        Branch newBranch = BranchFactory.createBranch(
                "B002", "Johannesburg North", "45 Rosebank Road, JHB"
        );
        ResponseEntity<Branch> response = branchController.create(newBranch);
        assertNotNull(response.getBody());
        assertEquals("Johannesburg North", response.getBody().getName());
    }

    @Test
    void read_shouldReturnBranch() {
        ResponseEntity<Branch> response = branchController.read(testBranch.getBranchId());
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Cape Town Central", response.getBody().getName());
    }

    @Test
    void update_shouldUpdateBranch() {
        testBranch.setName("Cape Town Waterfront");
        ResponseEntity<Branch> response = branchController.update(testBranch.getBranchId(), testBranch);
        assertNotNull(response.getBody());
        assertEquals("Cape Town Waterfront", response.getBody().getName());
    }

    @Test
    void delete_shouldRemoveBranch() {
        branchController.delete(testBranch.getBranchId());
        Optional<Branch> found = branchService.read(testBranch.getBranchId());
        assertFalse(found.isPresent());
    }

    @Test
    void getAll_shouldReturnAllBranches() {
        ResponseEntity<List<Branch>> response = branchController.getAll();
        assertFalse(response.getBody().isEmpty());
    }
}
