package ru.javawebinar.topjava.repository.jdbc.hsqldb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepositoryImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Profile(Profiles.HSQL_DB)
@Repository
public class HsqlJdbcMealRepositoyImpl extends JdbcMealRepositoryImpl <Timestamp> implements MealRepository {

    @Autowired
    public HsqlJdbcMealRepositoyImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    protected Timestamp prepareDate(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }
}
