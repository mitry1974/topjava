package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;

public interface MealService {

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id, int userId)throws NotFoundException;

    Meal save(Meal meal, int userId);

    Meal update(Meal meal, int userId)throws NotFoundException;

    Collection<Meal> getAll(int userId);

    Collection<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate);
}