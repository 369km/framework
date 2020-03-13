package org.example.business;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseService<T> {
    T save(T t);

    T findById(Long id);

    Page<T> findAll(T t, Pageable pageable);

    List<T> findAll();
}
