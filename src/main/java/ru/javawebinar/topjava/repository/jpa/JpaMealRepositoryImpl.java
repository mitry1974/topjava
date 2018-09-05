package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            return (get(meal.getId(), userId) == null) ? null : em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return (em.createNamedQuery("Meal.DELETE")
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0);
    }

    @Override
    public Meal get(int id, int userId) {
        Meal m = em.find(Meal.class, id);
        if (m != null && m.getUser().getId() == userId) {
            return m;
        }

        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery("Meal.GET_ALL_SORTED", Meal.class)
                .setParameter("user_id", userId).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery("Meal.GET_BETWEEN", Meal.class)
                .setParameter("user_id", userId)
                .setParameter("start_dt", startDate)
                .setParameter("end_dt", endDate)
                .getResultList();
    }
}