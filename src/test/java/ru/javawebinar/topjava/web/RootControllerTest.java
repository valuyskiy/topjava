package ru.javawebinar.topjava.web;

import org.junit.Test;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class RootControllerTest extends AbstractControllerTest {

    @Test
    public void testUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ)),
                                hasProperty("name", is(USER.getName()))
                        )
                )));
    }

    @Test
    public void testMeals() throws Exception {
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals", hasSize(6)))
                .andExpect(model().attribute("meals",
                        hasItem(
                                allOf(
                                        hasProperty("id", is(MEAL1.getId())),
                                        hasProperty("dateTime", is(MEAL1.getDateTime())),
                                        hasProperty("description", is(MEAL1.getDescription())),
                                        hasProperty("calories", is(MEAL1.getCalories())),
                                        hasProperty("exceed", is(isExceed(MEAL1)))
                                )
                        )
                ))
                .andExpect(model().attribute("meals",
                        hasItem(
                                allOf(
                                        hasProperty("id", is(MEAL4.getId())),
                                        hasProperty("dateTime", is(MEAL4.getDateTime())),
                                        hasProperty("description", is(MEAL4.getDescription())),
                                        hasProperty("calories", is(MEAL4.getCalories())),
                                        hasProperty("exceed", is(isExceed(MEAL4)))
                                )
                        )
                ));
    }

    private boolean isExceed(Meal meal) {
        return MealsUtil.getWithExceeded(MEALS, AuthorizedUser.getCaloriesPerDay())
                .stream()
                .filter(a -> a.getId() == meal.getId())
                .findFirst()
                .get()
                .isExceed();
    }
}