package co.za.carrental.service;

import co.za.carrental.domain.Service;
import java.util.List;

public interface IServiceEntityService {
    Service create(Service service);
    List<Service> getAll();
    Service getById(String id);
    void delete(String id);
}
