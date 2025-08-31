package co.za.carrental.service;

import co.za.carrental.domain.Admin;
import java.util.List;

public interface IAdminService {
    Admin create(Admin admin);
    List<Admin> getAll();
    Admin getById(String id);
    void delete(String id);
}
