package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})

@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_TEST_MEAL_ID, USER_ID);
        assertMatch(meal, USER_MEAL_2);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundGet() {
        service.get(ADMIN_TEST_MEAL_ID, USER_ID);
    }

    @Test
    public void delete() {
        service.delete(USER_TEST_MEAL_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), USER_MEAL_0, USER_MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        service.delete(ADMIN_TEST_MEAL_ID, USER_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> filtredMeals = service.getBetweenDates(
                DateTimeUtil.MIN_DATE, LocalDate.of(2018, 3, 16), ADMIN_ID
        );
        assertMatch(filtredMeals, ADMIN_MEAL_1, ADMIN_MEAL_2);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> mealsDateFiltered = service.getBetweenDateTimes(
                LocalDateTime.of(2018, 3, 15, 9, 0, 0),
                LocalDateTime.of(2018, 3, 17, 20, 45, 0),
                ADMIN_ID
        );
        assertMatch(mealsDateFiltered, ADMIN_MEAL_0, ADMIN_MEAL_1);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, USER_MEAL_0, USER_MEAL_1, USER_MEAL_2);
    }

    @Test
    public void update() {
        Meal newMeal = new Meal(USER_TEST_MEAL_ID, LocalDateTime.parse("2018-03-18T20:45:00"), "User ужин!!!!", 1500);
        Meal update = service.update(newMeal, USER_ID);
        assertMatch(service.get(update.getId(), USER_ID), newMeal);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundUpdate() {
        Meal newMeal = new Meal(ADMIN_TEST_MEAL_ID, LocalDateTime.parse("2018-03-17T20:45:00"), "Admin ужин", 1500);
        service.update(newMeal, USER_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.parse("2018-03-18T15:00:00"), "User кофе", 300);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.get(newMeal.getId(), USER_ID), newMeal);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMealCreate() {
        Meal newMeal = new Meal(LocalDateTime.parse("2018-03-18T12:30:00"), "User ланч", 800);
        service.create(newMeal, USER_ID);
    }
}