package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ru.javawebinar.topjava.repository.JpaUtil;

public abstract class AbstractJpaUserServiceTest extends AbstractUserServiceTest {
    @Autowired
    protected JpaUtil jpaUtil;

    @Autowired
    private ApplicationContext applicationContext;


    @Before
    public void setUp() throws Exception {
        super.setUp();
        JpaUtil ju = applicationContext.getBean(JpaUtil.class);
        ju.clear2ndLevelHibernateCache();
    }
}
