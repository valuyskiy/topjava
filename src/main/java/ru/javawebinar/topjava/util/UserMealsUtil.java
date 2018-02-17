package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        getFilteredWithExceededLoops(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }


    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> sumCaloriesPerDay = mealList
                .stream()
                .collect(Collectors.toMap(
                        d -> d.getDateTime().toLocalDate(),
                        UserMeal::getCalories,
                        (d1, d2) -> d1 + d2)
                );

        return mealList
                .stream()
                .filter(m -> TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime))
                .map(m -> new UserMealWithExceed(m.getDateTime(),
                        m.getDescription(),
                        m.getCalories(),
                        sumCaloriesPerDay.get(m.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }


    public static List<UserMealWithExceed> getFilteredWithExceededLoops(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMeal> filteredMeal = new ArrayList<>();
        Map<LocalDate, Integer> sumCaloriesPerDay = new HashMap<>();

        for (UserMeal meal : mealList) {
            sumCaloriesPerDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), (ov, nv) -> ov + nv);
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                filteredMeal.add(meal);
            }
        }


        List<UserMealWithExceed> result = new ArrayList<>();
        for (UserMeal meal : filteredMeal) {
            result.add(new UserMealWithExceed(meal.getDateTime(),
                    meal.getDescription(),
                    meal.getCalories(),
                    sumCaloriesPerDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
        }
        return result;
    }
}