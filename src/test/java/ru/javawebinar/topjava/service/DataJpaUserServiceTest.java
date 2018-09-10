package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.Comparator;
import java.util.stream.Collectors;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void testFetchMeal() {
        User u = service.fetchMeal(UserTestData.USER_ID);
        MealTestData.assertMatch(u.getMeals().stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList()), MealTestData.MEALS);
    }
}
