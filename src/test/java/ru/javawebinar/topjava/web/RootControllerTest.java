package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.to.MealWithExceed;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

class RootControllerTest extends AbstractControllerTest {

    @Test
    void testUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ)),
                                hasProperty("name", is(USER.getName()))
                        )
                )));
    }

    @Test
    void testMeals() throws Exception {
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals", hasSize(6)))
                .andExpect(model().attribute("meals", contains(new MealWithExceed(MEAL6.getId(), MEAL6.getDateTime(), MEAL6.getDescription(), MEAL6.getCalories(), true),
                        new MealWithExceed(MEAL5.getId(), MEAL5.getDateTime(), MEAL5.getDescription(), MEAL5.getCalories(), true),
                        new MealWithExceed(MEAL4.getId(), MEAL4.getDateTime(), MEAL4.getDescription(), MEAL4.getCalories(), true),
                        new MealWithExceed(MEAL3.getId(), MEAL3.getDateTime(), MEAL3.getDescription(), MEAL3.getCalories(), false),
                        new MealWithExceed(MEAL2.getId(), MEAL2.getDateTime(), MEAL2.getDescription(), MEAL2.getCalories(), false),
                        new MealWithExceed(MEAL1.getId(), MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories(), false))));
    }
}