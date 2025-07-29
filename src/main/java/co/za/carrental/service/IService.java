package co.za.carrental.service;

import java.util.List;
import java.util.Optional;

public interface IService<T, ID> {
    T create(T entity);
    Optional<T> read(ID id);
    T update(T entity);
    void delete(ID id);
    List<T> getAll();

    T save(T entity);

    Optional<T> findById(ID id);

    void deleteById(ID id);
}
