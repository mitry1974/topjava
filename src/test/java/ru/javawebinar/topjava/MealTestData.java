package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL1_ID = START_SEQ + 11;
    public static final int MEAL2_ID = START_SEQ + 12;
    public static final int MEAL3_ID = START_SEQ + 13;
    public static final int MEAL4_ID = START_SEQ + 14;
    public static final int MEAL5_ID = START_SEQ + 15;
    public static final int MEAL6_ID = START_SEQ + 16;
    public static final int MEAL7_ID = START_SEQ + 17;
    public static final int MEAL8_ID = START_SEQ + 18;
    public static final int MEAL9_ID = START_SEQ + 19;
    public static final int MEAL10_ID = START_SEQ + 20;
    public static final int MEAL11_ID = START_SEQ + 21;
    public static final int MEAL12_ID = START_SEQ + 22;
    public static final int MEAL_NOT_FOUND = START_SEQ + 23;

    public static final Meal MEAL1 = new Meal(MEAL1_ID, LocalDateTime.parse("2015-05-29T09:00"), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(MEAL2_ID, LocalDateTime.parse("2015-05-29T14:00"), "Обед", 1500);
    public static final Meal MEAL3 = new Meal(MEAL3_ID, LocalDateTime.parse("2015-05-29T20:00"), "Ужин", 500);
    public static final Meal MEAL4 = new Meal(MEAL4_ID, LocalDateTime.parse("2015-05-30T07:00"), "Завтрак", 500);
    public static final Meal MEAL5 = new Meal(MEAL5_ID, LocalDateTime.parse("2015-05-30T14:00"), "Обед", 1500);
    public static final Meal MEAL6 = new Meal(MEAL6_ID, LocalDateTime.parse("2015-05-30T18:00"), "Ужин", 500);
    public static final Meal MEAL7 = new Meal(MEAL7_ID, LocalDateTime.parse("2015-05-31T09:00"), "Завтрак", 500);
    public static final Meal MEAL8 = new Meal(MEAL8_ID, LocalDateTime.parse("2015-05-31T14:00"), "Обед", 1500);
    public static final Meal MEAL9 = new Meal(MEAL9_ID, LocalDateTime.parse("2015-05-31T20:00"), "Ужин", 500);
    public static final Meal MEAL10 = new Meal(MEAL10_ID, LocalDateTime.parse("2015-05-29T09:00"), "Завтрак", 500);
    public static final Meal MEAL11 = new Meal(MEAL11_ID, LocalDateTime.parse("2015-05-29T14:00"), "Обед", 1500);
    public static final Meal MEAL12 = new Meal(MEAL12_ID, LocalDateTime.parse("2015-05-29T20:00"), "Ужин", 500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
