package co.za.carrental.repository;

import co.za.carrental.domain.AdminPortal;
import org.springframework.data.jpa.repository.JpaRepository;

// Extends JpaRepository to give CRUD methods (save, findById, findAll, deleteById)
public interface AdminPortalRepository extends JpaRepository<AdminPortal, String> {
    // <EntityClass, TypeOfPrimaryKey>
}