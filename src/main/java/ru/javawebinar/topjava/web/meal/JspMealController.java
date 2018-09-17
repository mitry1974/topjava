package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController extends AbstractMealController {

    @Autowired
    public JspMealController(MealService service) {
        super(service);
    }

    @PostMapping("/filter")
    public String filter(HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        log.info("JspMealController @PostMapping(\"/filter\"), startDate {}, endDate {}, startTime{}, endTime {}", startDate, endDate, startTime, endTime);
        List<MealWithExceed> meals = getBetween(startDate, startTime, endDate, endTime);

        request.setAttribute("meals", meals);

        return "meals";
    }

    @PostMapping("/meals")
    public String meals(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        log.info("JspMealController, @PostMapping(\"/meals\")");
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
            service.create(meal, SecurityUtil.authUserId());
        } else {
            meal.setId(getId(request));
            service.update(meal, SecurityUtil.authUserId());
        }
        return "redirect:meals";
    }

    @GetMapping("/meals")
    public String root(HttpServletRequest request) {
        log.info("JspMealController @GetMapping(\"/meals\")");
        request.setAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping("/deleteMeal")
    public String delete(HttpServletRequest request) {
        super.delete(getId(request));
        log.info("JspMealController  @GetMapping(\"/deleteMeal\")");
        return "redirect:meals";
    }

    @GetMapping("/createMeal")
    public String create(HttpServletRequest request) {
        log.info("JspMealController @GetMapping(\"/createMeal\")");
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        request.setAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/updateMeal")
    public String update(HttpServletRequest request) {
        log.info("JspMealController @GetMapping(\"/updateMeal\")");
        final Meal meal = service.get(getId(request), SecurityUtil.authUserId());
        request.setAttribute("meal", meal);
        return "mealForm";
    }
}
