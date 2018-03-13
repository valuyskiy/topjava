package ru.javawebinar.topjava;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class AuthorizedUser {

    private static int id = 2;

    public static void setId(int id) {
        AuthorizedUser.id = id;
    }

    public static int getId() {
        return AuthorizedUser.id;
    }

    public static int getCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}