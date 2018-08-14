package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {
    Meal save(Meal meal, Integer userId);

    boolean delete(Integer id, Integer userId);

    Meal get(Integer id, Integer userId);

    Collection<Meal> getAll(Integer userId);
}
