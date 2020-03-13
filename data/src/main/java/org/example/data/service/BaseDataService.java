package org.example.data.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BaseDataService<T> {
    T save(T t);

    List<T> saveAll(List<T> list);

    List<T> findAll();

    Page<T> findAll(Example<T> example, Pageable pageable);

    Optional<T> findById(Long id);

    void deleteById(Long id);
}
