package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.FilterData;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private FilterData filterData = new FilterData();

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal save(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.save(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public Meal update(Meal meal, int id) {
        log.info("delete {}", id);
        assureIdConsistent(meal, id);
        return service.update(meal, authUserId());
    }

    public Meal get(int id){
        log.info("get {}", id);
        return service.get(id, authUserId());
    }

    public Collection<MealWithExceed> getAll() {
        log.info("getAll {}", authUserId());
        return MealsUtil.getWithExceeded(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Collection<MealWithExceed> getFilteredWithExceed(){
        log.info("getFilteredMeal {}", authUserId());
        return service.getFiltered(authUserId(), MealsUtil.DEFAULT_CALORIES_PER_DAY, filterData);
    }

    public void setFilterData(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        filterData.set(startDate, endDate, startTime, endTime);
    }

    public FilterData getFilterData(){
        return filterData;
    }
}