package com.exchange.dao.jdbc;

import com.exchange.dao.User;
import com.exchange.dao.UserDao;
import com.exchange.dao.jdbc.mapper.model.UserRowMapper;
import com.exchange.dto.user.UserUpdatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * The type User dao.
 */
@Repository
public class UserDaoImpl implements UserDao {

    /**
     * The constant ID.
     */
    public static final String ID = "id";
    /**
     * The constant USER_NAME.
     */
    public static final String USER_NAME = "user_name";
    /**
     * The constant USER_PASSWORD.
     */
    public static final String USER_PASSWORD = "user_password";
    /**
     * The constant USER_GENDER.
     */
    public static final String USER_GENDER = "user_gender";
    /**
     * The constant USER_BIRTH_DATE.
     */
    public static final String USER_BIRTH_DATE = "user_birth_date";
    /**
     * The constant USER_INFORMATION.
     */
    public static final String USER_INFORMATION = "user_information";
    private static final String LIMIT = "limit";
    private static final String OFFSET = "offset";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UserRowMapper userRowMapper;

    @Value("${user.selectByLimitAndOffset}")
    private String selectByLimitAndOffsetSql;
    @Value("${user.insert}")
    private String insertUserSql;
    @Value("${user.update}")
    private String updateUserSql;
    @Value("${user.delete}")
    private String deleteUserSql;
    @Value("${user.checkUserByLogin}")
    private String checkUserByLoginSql;
    @Value("${user.selectUsersCount}")
    private String selectUsersCountSql;

    /**
     * Instantiates a new User dao.
     *
     * @param jdbcTemplate               the jdbc template
     * @param namedParameterJdbcTemplate the named parameter jdbc template
     * @param userRowMapper              the user row mapper
     */
    @Autowired
    public UserDaoImpl(
            final JdbcTemplate jdbcTemplate,
            final NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            final UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public List<User> getUsersByLimitAndOffset(final Integer limit, final Integer offset) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(LIMIT, limit);
        parameterSource.addValue(OFFSET, offset);
        return namedParameterJdbcTemplate.query(selectByLimitAndOffsetSql, parameterSource, userRowMapper);
    }

    @Override
    public Long addUser(final User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(USER_NAME, user.getName());
        this.fillParameterSourceByPasswordAndGenderAndBirthDateAndInformation(
                parameterSource,
                user.getPassword(),
                user.getGender(),
                user.getBirthDate(),
                user.getInformation());
        namedParameterJdbcTemplate.update(
                insertUserSql,
                parameterSource,
                keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void fillParameterSourceByPasswordAndGenderAndBirthDateAndInformation(
            final MapSqlParameterSource parameterSource,
            final String password,
            final Boolean gender,
            final LocalDate birthDate,
            final String information) {
        parameterSource.addValue(USER_PASSWORD, password);
        parameterSource.addValue(USER_GENDER, gender);
        parameterSource.addValue(USER_BIRTH_DATE, birthDate);
        parameterSource.addValue(USER_INFORMATION, information);
    }

    @Override
    public Boolean updateUser(final UserUpdatingDto userUpdatingDto) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(ID, userUpdatingDto.getId());
        this.fillParameterSourceByPasswordAndGenderAndBirthDateAndInformation(
                parameterSource, userUpdatingDto.getPassword(),
                userUpdatingDto.getGender(),
                userUpdatingDto.getBirthDate(),
                userUpdatingDto.getInformation());
        return namedParameterJdbcTemplate.update(updateUserSql, parameterSource) == 1;
    }

    @Override
    public Boolean deleteUser(final Long userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(ID, userId);
        return namedParameterJdbcTemplate.update(deleteUserSql, parameterSource) == 1;
    }

    @Override
    public Boolean checkUserByLogin(final String userName) {
        return jdbcTemplate.queryForObject(checkUserByLoginSql, new String[]{userName}, Boolean.class);
    }

    @Override
    public Long getUserCount() {
        return jdbcTemplate.queryForObject(selectUsersCountSql, Long.class);
    }

}
