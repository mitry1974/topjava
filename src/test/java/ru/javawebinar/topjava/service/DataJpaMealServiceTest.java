package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {
    @Test
    public void testFetchUser() {
        Meal m = service.fetchUser(MEAL1_ID);
        assertMatch(m, MEAL1);
        UserTestData.assertMatch(m.getUser(), UserTestData.USER);
    }
}
