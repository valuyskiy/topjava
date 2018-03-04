package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.dao.MealMemoryDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    Dao<Meal> dao = MealMemoryDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));

        long id = Long.parseLong(request.getParameter("id") != null ? request.getParameter("id") : "-1");
        String action = request.getParameter("action") == null ? "" : request.getParameter("action").toLowerCase();

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
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");

            long id = Long.parseLong(request.getParameter("id").isEmpty() ? "-1" : request.getParameter("id"));
            String description = request.getParameter("description");
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
            int calories = Integer.parseInt(request.getParameter("calories"));
            String action = request.getParameter("action") == null ? "" : request.getParameter("action").toLowerCase();

            if (action.equals("edit")) {
                if (id >= 0) {
                    log.debug("update meal: id = " + id);
                    Meal meal = new Meal(dateTime, description, calories);
                    meal.setId(id);
                    dao.update(meal);
                } else {
                    log.debug("add new meal");
                    dao.add(new Meal(dateTime, description, calories));
                }
            }
        } catch (Exception e) {
            log.debug("Data error");
            request.setAttribute("errorMessage", "Ошибка ввода данных");
            request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }

        request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        response.sendRedirect("meals");
    }
}
