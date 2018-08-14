package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> this.save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        Map<Integer, Meal> mealMap = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealMap.put(userId, meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return mealMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(Integer id, Integer userId) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap!=null && mealMap.remove(id) != null;
    }

    @Override
    public Meal get(Integer id, Integer userId) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap!=null ?null:mealMap.get(id);
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        return repository.get(userId).values().stream().sorted(Comparator.comparing(Meal::getDate).reversed()).collect(Collectors.toList());
    }
}

