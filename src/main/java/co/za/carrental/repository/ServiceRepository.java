package co.za.carrental.repository;

import co.za.carrental.domain.CarService; // Import the domain object CarService
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Crucial: Changed Integer to String for the ID type
public interface ServiceRepository extends JpaRepository<CarService, String> {
    // JpaRepository provides save(), findById(ID), findAll(), deleteById(ID)
}
/*ServiceRepository.java
Service Repository
Thabiso Kama
25 May 2025
*/