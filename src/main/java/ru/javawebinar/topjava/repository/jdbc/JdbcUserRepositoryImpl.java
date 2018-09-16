package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            saveRoles(newKey.intValue(), user.getRoles().toArray(new Role[0]));

        } else {
            if(jdbcTemplate.update("DELETE FROM user_roles WHERE user_roles.user_id=?", user.getId()) == 0){
                return null;
            }

            if (namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
                return null;
            }

            saveRoles(user.getId(), user.getRoles().toArray(new Role[0]));
        }
        return user;
    }

    private void saveRoles(int user_id, Role[] roles){
        MapSqlParameterSource[] mps = new MapSqlParameterSource[roles.length];
        for (int i = 0; i < roles.length; i++) {
            mps[i] = new MapSqlParameterSource().addValue("user_id", user_id, Types.INTEGER).addValue("role", roles[i], Types.VARCHAR);
        }

        namedParameterJdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES(:user_id, :role)", mps);
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles u on users.id = u.user_id WHERE id=?", new UserListExtractor(), id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users  LEFT JOIN user_roles u on users.id = u.user_id WHERE email=?", new UserListExtractor(), email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles u on users.id = u.user_id ORDER BY name, email", new UserListExtractor());
    }

    public class UserListExtractor implements ResultSetExtractor<List<User>> {
        public List<User> extractData(ResultSet rs) throws DataAccessException, SQLException {
            Map<Integer, User> users = new LinkedHashMap<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                Role role = Role.valueOf(rs.getString("role"));
                User user = users.get((id));
                if (user == null) {
                    users.put(id, new User(id,
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getInt("calories_per_day"),
                            rs.getBoolean("enabled"),
                            rs.getDate("registered"),
                            Arrays.asList(role)));
                } else {
                    user.addRole(role);
                }
            }
            return users.values().stream().collect(Collectors.toList());
        }
    }
}
