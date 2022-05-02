package ru.az.secu.services;

import java.awt.print.Pageable;
import java.util.List;

public interface CrudService<T> {

    T add(T t);
    T update(T t) throws MyException;
    void delete(T t);
    void delete(Long id);
    T findById(Long id) throws MyException;
    long count();
    List<T> findAll() throws MyException;
    List<T> findAll(Pageable pageable);

}
