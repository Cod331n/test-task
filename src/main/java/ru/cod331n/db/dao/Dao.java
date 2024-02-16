package ru.cod331n.db.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    void createTable();
    Optional<T> get(long id);

    List<T> getAll();

    void save(T type);

    void update(long id, T type);

    void delete(T type);
}

