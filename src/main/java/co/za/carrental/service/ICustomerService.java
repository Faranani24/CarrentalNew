package co.za.carrental.service;

import co.za.carrental.domain.Customer;

import java.util.List;

public interface ICustomerService extends IService<Customer, String> {

    List<Customer> getAll();
}
