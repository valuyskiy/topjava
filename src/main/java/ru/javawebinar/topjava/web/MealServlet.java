package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.dao.MealMemoryDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    private Dao<Meal> dao;

    @Override
    public void init() throws ServletException {
        dao = new MealMemoryDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        request.setAttribute("meals", getExceededList());

        long id = getId(request);
        String action = getAction(request);

        switch (action) {
            case "edit":
                request.setAttribute("editMeal", dao.getById(id));
                break;
            case "delete":
                log.debug("delete meal: id = " + id);
                dao.delete(id);
                response.sendRedirect("meals");
                return;
        }
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            String action = getAction(request);

            if (action.equals("edit")) {
                long id = getId(request);
                String description = request.getParameter("description");
                LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
                int calories = Integer.parseInt(request.getParameter("calories"));
                Meal meal = new Meal(dateTime, description, calories);

                if (id >= 0) {
                    meal.setId(id);
                    log.debug("update meal: " + getDiff(dao.save(meal), meal));
                } else {
                    dao.save(meal);
                    log.debug("add new " + meal.toString());
                }
            }
        } catch (Exception e) {
            log.debug("Data error");
            request.setAttribute("errorMessage", "Ошибка ввода данных");
            request.setAttribute("meals", getExceededList());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
        response.sendRedirect("meals");
    }

    private String getAction(HttpServletRequest request) {
        return request.getParameter("action") == null
                ? ""
                : request.getParameter("action").toLowerCase();
    }

    private long getId(HttpServletRequest request) {
        return (request.getParameter("id") == null || request.getParameter("id").isEmpty())
                ? Long.MIN_VALUE
                : Long.parseLong(request.getParameter("id"));
    }

    private List<MealWithExceed> getExceededList() {
        return MealsUtil.getFilteredWithExceeded(dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
    }

    private String getDiff(Meal oldEntry, Meal newEntry) {
        String result = "id = " + oldEntry.getId() + " :";
        if (!oldEntry.getDateTime().equals(newEntry.getDateTime())) {
            result += " " + oldEntry.getDateTime().toString() + " -> " + newEntry.getDateTime().toString() + " ";
        }
        if (!oldEntry.getDescription().equals(newEntry.getDescription())) {
            result += " '" + oldEntry.getDescription() + "' -> '" + newEntry.getDescription() + "' ";
        }
        if (oldEntry.getCalories() != newEntry.getCalories()) {
            result += " " + oldEntry.getCalories() + " -> " + newEntry.getCalories() + " ";
        }
        return result;
    }
}
