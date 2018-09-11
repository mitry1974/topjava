package ru.javawebinar.topjava.service;

import org.junit.experimental.categories.Category;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@Category(ru.javawebinar.topjava.service.JpaUserServiceTest.class)
@ActiveProfiles(Profiles.JPA)
public class JpaUserServiceTest extends AbstractUserServiceTest {
}
