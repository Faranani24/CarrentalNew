// src/test/java/co/za/carrental/controller/BranchControllerTest.java
package co.za.carrental.controller;

import co.za.carrental.domain.Branch;
import co.za.carrental.factory.BranchFactory;
import co.za.carrental.repository.BranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BranchControllerTest {

    @Autowired
    private BranchController branchController;

    @Autowired
    private BranchRepository branchRepository;

    private Branch testBranch;

    @BeforeEach
    void setUp() {
        branchRepository.deleteAll();
        testBranch = BranchFactory.buildBranch("123 Main Street, Cape Town", "021-123-4567");
        branchRepository.save(testBranch);
    }

    @Test
    void create_shouldCreateBranch() {
        Branch newBranch = BranchFactory.buildBranch("45 Rosebank Road, JHB", "011-987-6543");
        ResponseEntity<Branch> response = branchController.create(newBranch);

        assertNotNull(response.getBody());
        assertEquals("45 Rosebank Road, JHB", response.getBody().getAddress());
        assertEquals(2, branchRepository.count());
    }

    @Test
    void getAll_shouldReturnAllBranches() {
        ResponseEntity<List<Branch>> response = branchController.getAll();

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void read_shouldReturnBranchById() {
        ResponseEntity<Branch> response = branchController.read(testBranch.getBranchId());

        assertNotNull(response.getBody());
        assertEquals("123 Main Street, Cape Town", response.getBody().getAddress());
    }

    @Test
    void update_shouldUpdateBranch() {
        testBranch.setAddress("Updated Address");
        ResponseEntity<Branch> response = branchController.update(testBranch.getBranchId(), testBranch);

        assertNotNull(response.getBody());
        assertEquals("Updated Address", response.getBody().getAddress());
    }

    @Test
    void delete_shouldDeleteBranch() {
        branchController.delete(testBranch.getBranchId());
        assertEquals(0, branchRepository.count());
    }
}