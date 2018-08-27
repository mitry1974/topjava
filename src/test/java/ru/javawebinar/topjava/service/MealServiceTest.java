package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
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
    public void create() {
        Meal meal = new Meal(LocalDateTime.now(), "Тестовая еда 1", 500);
        Meal created = service.update(meal, USER_ID);
        meal.setId(created.getId());
        assertMatch(meal, created);
    }

    @Test
    public void get() {
        Meal meal = service.get(MEAL1_ID, USER_ID);
        assertMatch(meal, MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(MEAL_NOT_FOUND, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getWrongUser() {
        service.get(MEAL1_ID, UserTestData.USER_NOT_FOUND_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL1_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(MEAL_NOT_FOUND, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWrongUser() {
        service.delete(MEAL1_ID, UserTestData.USER_NOT_FOUND_ID);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, MEAL9, MEAL8, MEAL7, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void update() {
        Meal m = service.get(MEAL1_ID, USER_ID);
        m.setDateTime(LocalDateTime.now());
        m.setDescription("Полдник");
        m.setCalories(20000);
        Meal updated = service.update(m, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), m);
    }

    @Test(expected = NotFoundException.class)
    public void updateWrongUser() {
        Meal m = service.get(MEAL1_ID, USER_ID);
        m.setDateTime(LocalDateTime.now());
        m.setDescription("Полдник");
        m.setCalories(20000);
        Meal updated = service.update(m, UserTestData.USER_NOT_FOUND_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateForeign() {
        Meal m = service.get(MEAL1_ID, USER_ID);
        m.setDateTime(LocalDateTime.now());
        m.setDescription("Полдник");
        m.setCalories(20000);
        Meal updated = service.update(m, UserTestData.ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> meals = service.getBetweenDates(LocalDate.parse("2015-05-29"), LocalDate.parse("2015-05-30"), USER_ID);
        assertMatch(meals, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> meals = service.getBetweenDateTimes(LocalDateTime.parse("2015-05-29T07:00"), LocalDateTime.parse("2015-05-30T11:00"), USER_ID);
        assertMatch(meals, MEAL4, MEAL3, MEAL2, MEAL1);
    }
}
