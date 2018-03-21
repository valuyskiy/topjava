package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final int USER_TEST_MEAL_ID = 100002;
    public static final int ADMIN_TEST_MEAL_ID = 100007;

    public static final Meal USER_MEAL_0 = new Meal(100004, LocalDateTime.parse("2018-03-18T19:00:00"), "User ужин", 700);
    public static final Meal USER_MEAL_1 = new Meal(100003, LocalDateTime.parse("2018-03-18T12:30:00"), "User обед", 1000);
    public static final Meal USER_MEAL_2 = new Meal(100002, LocalDateTime.parse("2018-03-18T09:10:00"), "User завтрак", 500);

    public static final Meal ADMIN_MEAL_0 = new Meal(100007, LocalDateTime.parse("2018-03-17T20:45:00"), "Admin ужин", 500);
    public static final Meal ADMIN_MEAL_1 = new Meal(100006, LocalDateTime.parse("2018-03-16T13:00:00"), "Admin обед", 1100);
    public static final Meal ADMIN_MEAL_2 = new Meal(100005, LocalDateTime.parse("2018-03-15T08:55:00"), "Admin завтрак", 300);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
