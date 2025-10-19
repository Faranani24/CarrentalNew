package co.za.carrental.service;

import co.za.carrental.domain.AdminPortal;
import java.util.List;
import java.util.Optional;


public interface IAdminPortalService {
    AdminPortal create(AdminPortal portal);
    Optional<AdminPortal> read(String id);
    AdminPortal update(AdminPortal portal);
    void delete(String id);
    List<AdminPortal> getAll();
}