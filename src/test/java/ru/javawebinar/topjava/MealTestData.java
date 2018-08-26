package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL1_ID        = START_SEQ + 11;
    public static final int MEAL2_ID        = START_SEQ + 12;
    public static final int MEAL3_ID        = START_SEQ + 13;
    public static final int MEAL_NOT_FOUND  = START_SEQ + 20;
    public static final Meal MEAL1 = new Meal(MEAL1_ID, LocalDateTime.parse("2015-05-29T09:00"), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(MEAL2_ID, LocalDateTime.parse("2015-05-29T14:00"), "Обед", 1500);
    public static final Meal MEAL3 = new Meal(MEAL3_ID, LocalDateTime.parse("2015-05-29T20:00"), "Ужин", 500);

    public static void assertMatch(Meal actual, Meal expected){
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
