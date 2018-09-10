package ru.javawebinar.topjava;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.repository.DaoTest;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles("jpa")
public class JpaMealServiceTest  extends MealServiceTest {
}
