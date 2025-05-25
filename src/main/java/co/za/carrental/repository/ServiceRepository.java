package co.za.carrental.repository;

import co.za.carrental.domain.CarService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<CarService, Integer>{
}
/*ServiceRepository.java
CarService Repository
Thabiso Kama
25 May 2025
 */