package org.example.mocktest_test2.RepoInterface;

import java.util.List;

public interface Repository<T> {
    List<T> findAll();
    T findById(int id);
    void save(T entity);
    void update(T entity);
    void delete(int id);
}
