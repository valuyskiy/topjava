package ru.javawebinar.topjava.dao;

import java.util.List;

public interface Dao<T> {
    T save(T t);

    void delete(long id);

    List<T> getAll();

    T getById(long id);
}
