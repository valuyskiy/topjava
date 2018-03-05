package ru.javawebinar.topjava.dao;

import java.util.List;

public interface Dao<T> {
    T add(T t);

    void delete(long id);

    T update(T t);

    List<T> getAll();

    T getById(long id);
}
