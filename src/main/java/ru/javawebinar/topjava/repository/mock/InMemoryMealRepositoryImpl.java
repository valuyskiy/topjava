package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            if (meal.getUserId() == null) {
                meal.setUserId(AuthorizedUser.id());
            }
            repository.put(meal.getId(), meal);
            return meal;
        }
        if (meal.getUserId() == AuthorizedUser.id()) {
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (repository.get(id) != null) {
            return repository.get(id).getUserId() == AuthorizedUser.id() && repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id) {
        if (repository.get(id) != null) {
            return repository.get(id).getUserId() == AuthorizedUser.id() ? repository.get(id) : null;
        }
        return null;
    }

    @Override
    public List<Meal> getAll() {
        return repository
                .values()
                .stream()
                .filter(meal -> meal.getUserId() == AuthorizedUser.id())
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(toList());
    }
}

