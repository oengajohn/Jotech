package io.jotech.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> extends Repository<T, ID> {
    long count();

    void delete(T entity);

    void deleteAll();

    void deleteAll(List<T> entities);

    void deleteById(ID id);

    boolean existsById(ID id);

    List<T> findAll();

    List<T> findAllById(List<ID> ids);

    Optional<T> findById(ID id);

    List<T> findRange(int[] range);

    T save(T entity);

    List<T> saveAll(List<T> entities);

    T update(T entity);

    List<T> updateAll(List<T> entities);

}
