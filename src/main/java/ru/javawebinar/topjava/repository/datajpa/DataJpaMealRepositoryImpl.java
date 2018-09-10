package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {
    private final Sort SORT_DATETIME = new Sort(Sort.Direction.DESC, "date_time");

    @Autowired
    private CrudMealRepository crudRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        return crudRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal m = get(id, userId);
        return m != null && crudRepository.delete(id) == 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.findById(id).filter(m->m.getUser().getId()==userId).orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAll(SORT_DATETIME);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository.getBetween(startDate, endDate);
    }
}
