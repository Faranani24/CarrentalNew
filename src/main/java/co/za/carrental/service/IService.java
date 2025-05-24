package co.za.carrental.service;

import co.za.carrental.domain.Customer;

import java.util.Optional;

public interface IService <T, ID>{

    T create(T t);

    Optional<Customer> read(ID id);

    T update(T t);

    void delete(ID id);
}
