
package co.za.carrental.service;


import co.za.carrental.domain.Booking;
import co.za.carrental.domain.Customer;

import java.util.Optional;

public interface IService <T, ID>{

    T create(T t);


    Optional<Booking> read(ID id);


    T update(T t);

    void delete(ID id);

    Object save(Customer any);

    Object findById(String cust1);

    Object findAll();

    void deleteById(String cust1);
}
