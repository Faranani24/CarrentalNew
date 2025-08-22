package co.za.carrental.service.impl;

import co.za.carrental.domain.Branch;
import co.za.carrental.factory.BranchFactory;
import co.za.carrental.service.IBranchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BranchServiceImplTest {

    @Autowired
    private IBranchService branchService;

    private Branch testBranch;

    @BeforeEach
    void setUp() {
        String address = "77A Jolobe Street, Luzuko Park";
        String phone = "074065671";

        testBranch = BranchFactory.buildBranch(address, phone);
        testBranch = branchService.save(testBranch);

        assertNotNull(testBranch);
        assertNotNull(testBranch.getBranchId());
    }

    @Test
    void read_shouldReturnBranchById() {
        Optional<Branch> found = branchService.findById(testBranch.getBranchId());
        assertTrue(found.isPresent());
        assertEquals(testBranch.getBranchId(), found.get().getBranchId());
    }

    @Test
    void create_shouldSaveBranch() {
        Branch newBranch = BranchFactory.buildBranch("99 New Road, Cape Town", "0215554444");
        Branch saved = branchService.save(newBranch);
        assertNotNull(saved);
        assertNotNull(saved.getBranchId());
    }

    @Test
    void update_shouldUpdateBranch() {
        testBranch.setAddress("99 Updated Road, Cape Town");
        testBranch.setPhone("0219998888");
        Branch updated = branchService.update(testBranch);
        assertNotNull(updated);
        assertEquals(testBranch.getBranchId(), updated.getBranchId());
        assertEquals("99 Updated Road, Cape Town", updated.getAddress());
        assertEquals("0219998888", updated.getPhone());
    }

    @Test
    void delete_shouldRemoveBranchById() {
        branchService.deleteById(testBranch.getBranchId());
        Optional<Branch> found = branchService.findById(testBranch.getBranchId());
        assertFalse(found.isPresent());
    }
}