package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.FilterData;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

public interface MealService {

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id, int userId);

    Meal save(Meal meal, int userId);

    Meal update(Meal meal, int userId);

    Collection<Meal> getAll(int userId);

    Collection<MealWithExceed> getFiltered(int userId, int calories, FilterData filterData);
}