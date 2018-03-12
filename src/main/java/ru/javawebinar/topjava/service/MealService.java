package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public interface MealService {

    void delete(int id) throws NotFoundException;

    Meal get(int id) throws NotFoundException;

    Meal save(Meal meal);

    List<MealWithExceed>  getAll();

}