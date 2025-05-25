package co.za.carrental.repository;

import co.za.carrental.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, String>{
}
