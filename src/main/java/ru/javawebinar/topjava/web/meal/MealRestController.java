package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
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
    private final MealService service;
    private LocalDate startDate = LocalDate.MIN;
    private LocalDate endDate = LocalDate.MAX;
    private LocalTime startTime = LocalTime.MIN;
    private LocalTime endTime = LocalTime.MAX;
    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public String getStartDate() {
        return startDate == LocalDate.MIN ? "" : startDate.toString();
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate.isEmpty() ? LocalDate.MIN : LocalDate.parse(startDate);
    }

    public String getEndDate() {
        return endDate == LocalDate.MAX ? "" : endDate.toString();
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate.isEmpty() ? LocalDate.MAX : LocalDate.parse(endDate);
    }

    public String getStartTime() {
        return startTime == LocalTime.MIN ? "" : startTime.toString();
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime.isEmpty() ? LocalTime.MIN : LocalTime.parse(startTime);
    }

    public String getEndTime() {
        return endTime == LocalTime.MAX ? "" : endTime.toString();
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime.isEmpty() ? LocalTime.MAX : LocalTime.parse(endTime);
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

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, authUserId());
    }

    public Collection<MealWithExceed> getAll() {
        log.info("getAll {}", authUserId());
        return MealsUtil.getWithExceeded(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Collection<MealWithExceed> getFilteredWithExceed() {
        log.info("getFilteredMeal {}", authUserId());
        return MealsUtil.getFilteredWithExceeded(
                service.getFiltered(authUserId(), startDate, endDate),
                MealsUtil.DEFAULT_CALORIES_PER_DAY,
                startTime,
                endTime);
    }
}
