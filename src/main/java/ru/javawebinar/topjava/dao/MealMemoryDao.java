package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealMemoryDao implements Dao<Meal> {

    private Map<Long, Meal> mealStorage = new ConcurrentHashMap();
    private AtomicLong nextId = new AtomicLong(0L);

    private MealMemoryDao() {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 8, 15), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 45), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
                new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 11, 0), "Завтрак", 300),
                new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 15, 30), "Ланч", 1000),
                new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 17, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.JUNE, 2, 10, 0), "Завтрак", 400),
                new Meal(LocalDateTime.of(2015, Month.JUNE, 2, 13, 0), "Ланч", 1500),
                new Meal(LocalDateTime.of(2015, Month.JUNE, 2, 22, 15), "Вечерний перекус", 310)
        );

        for (Meal meal : meals) {
            add(meal);
        }
    }

    private static class MealMemoryDaoHolder {
        private final static MealMemoryDao instance = new MealMemoryDao();
    }

    public static MealMemoryDao getInstance() {
        return MealMemoryDaoHolder.instance;
    }

    @Override
    public void add(Meal meal) {
        meal.setId(nextId.get());
        mealStorage.put(nextId.getAndIncrement(), meal);
    }

    @Override
    public void delete(long id) {
        mealStorage.remove(id);
    }

    @Override
    public void update(Meal meal) {
        mealStorage.put(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealStorage.values());
    }

    @Override
    public Meal getById(long id) {
        return mealStorage.get(id);
    }
}
