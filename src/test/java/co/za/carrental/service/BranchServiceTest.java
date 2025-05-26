package co.za.carrental.service;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import co.za.carrental.domain.Branch;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import co.za.carrental.repository.BranchRepository;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BranchServiceTest {
    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchService branchService;

    private Branch branch;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        branch = new Branch.Builder() // Ensure Branch.Builder exists in your project
                .branchId("12121")
                .address("77 Long Street, Cape Town")
                .phone("0211256969")
                .build();
    }

    @Test
    public void testSaveBranch() {
        when(branchRepository.save(branch)).thenReturn(branch);
        Branch saved = branchService.save(branch);
        assertNotNull(saved);
        assertEquals("12121", saved.getBranchId());
        verify(branchRepository, times(1)).save(branch);
    }

    @Test
    public void testFindBranchById() {
        when(branchRepository.findById("12121")).thenReturn(Optional.of(branch));
        Optional<Branch> found = branchService.findById("12121");
        assertTrue(found.isPresent());
        assertEquals("12121", found.get().getBranchId());
    }

    @Test
    public void testFindAllBranches() {
        List<Branch> branchList = Arrays.asList(branch);
        when(branchRepository.findAll()).thenReturn(branchList);
        List<Branch> result = branchService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void testDeleteBranchById() {
        doNothing().when(branchRepository).deleteById("12121");
        branchService.deleteById("12121");
        verify(branchRepository, times(1)).deleteById("12121");
    }

    @Test
    public void testFindByAddressContaining() {
        when(branchRepository.findByAddressContainingIgnoreCase("cape"))
                .thenReturn(List.of(branch));
        List<Branch> result = branchService.findByAddressContaining("cape");
        assertFalse(result.isEmpty());
        assertEquals("123 Main Road, Cape Town", result.get(0).getAddress());
    }
}
