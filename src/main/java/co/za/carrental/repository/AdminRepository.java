package co.za.carrental.repository;

import co.za.carrental.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String>{
    Admin findByEmail(String email);
}
