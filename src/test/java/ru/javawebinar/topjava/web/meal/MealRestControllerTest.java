package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.contentJson;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Autowired
    protected MealService mealService;


    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(mealService.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);

    }

    @Test
    void testCreate() throws Exception {
        Meal expected = new Meal(LocalDateTime.now(), "created", 500);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Meal returned = TestUtil.readFromJson(action, Meal.class);
        expected.setId(returned.getId());
        assertMatch(returned, expected);
    }

    @Test
    void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("Updated description");

        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(mealService.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(new MealWithExceed(MEAL6.getId(), MEAL6.getDateTime(), MEAL6.getDescription(), MEAL6.getCalories(), true),
                        new MealWithExceed(MEAL5.getId(), MEAL5.getDateTime(), MEAL5.getDescription(), MEAL5.getCalories(), true),
                        new MealWithExceed(MEAL4.getId(), MEAL4.getDateTime(), MEAL4.getDescription(), MEAL4.getCalories(), true),
                        new MealWithExceed(MEAL3.getId(), MEAL3.getDateTime(), MEAL3.getDescription(), MEAL3.getCalories(), false),
                        new MealWithExceed(MEAL2.getId(), MEAL2.getDateTime(), MEAL2.getDescription(), MEAL2.getCalories(), false),
                        new MealWithExceed(MEAL1.getId(), MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories(), false)));
    }

    @Test
    void testGetBetween() throws Exception {
        final String uri = REST_URL + "filter" +
                "?startDateTime=" + LocalDateTime.of(2015, Month.MAY, 31, 11, 0) +
                "&endDateTime=" + LocalDateTime.of(2015, Month.MAY, 31, 21, 0);


        mockMvc.perform(get(uri))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(
                        new MealWithExceed(MEAL5.getId(), MEAL5.getDateTime(), MEAL5.getDescription(), MEAL5.getCalories(), true),
                        new MealWithExceed(MEAL6.getId(), MEAL6.getDateTime(), MEAL6.getDescription(), MEAL6.getCalories(), true)));
    }

    @Test
    void testGetBetweenFilter() throws Exception {
        String uri = REST_URL + "filterAll" +
                "?startDate=" + LocalDate.of(2015, Month.MAY, 31) +
                "&endDate=" + LocalDate.of(2015, Month.MAY, 31);

        mockMvc.perform(get(uri))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(
                        new MealWithExceed(MEAL4.getId(), MEAL4.getDateTime(), MEAL4.getDescription(), MEAL4.getCalories(), true),
                        new MealWithExceed(MEAL5.getId(), MEAL5.getDateTime(), MEAL5.getDescription(), MEAL5.getCalories(), true),
                        new MealWithExceed(MEAL6.getId(), MEAL6.getDateTime(), MEAL6.getDescription(), MEAL6.getCalories(), true)));

        uri = REST_URL + "filterAll?";

        mockMvc.perform(get(uri))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(
                        new MealWithExceed(MEAL1.getId(), MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories(), false),
                        new MealWithExceed(MEAL2.getId(), MEAL2.getDateTime(), MEAL2.getDescription(), MEAL2.getCalories(), false),
                        new MealWithExceed(MEAL3.getId(), MEAL3.getDateTime(), MEAL3.getDescription(), MEAL3.getCalories(), false),
                        new MealWithExceed(MEAL4.getId(), MEAL4.getDateTime(), MEAL4.getDescription(), MEAL4.getCalories(), true),
                        new MealWithExceed(MEAL5.getId(), MEAL5.getDateTime(), MEAL5.getDescription(), MEAL5.getCalories(), true),
                        new MealWithExceed(MEAL6.getId(), MEAL6.getDateTime(), MEAL6.getDescription(), MEAL6.getCalories(), true)));
    }
}
