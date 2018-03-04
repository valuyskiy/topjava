package ru.javawebinar.topjava.dao;

import java.util.List;

public interface Dao<T> {
    void add(T t);

    void delete(long id);

    void update(T t);

    List<T> getAll();

    T getById(long id);
}
