package co.za.carrental.repository;

import co.za.carrental.domain.CarType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarTypeRepository extends JpaRepository<CarType, String> {
}

