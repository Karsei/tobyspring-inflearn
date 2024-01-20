package kr.pe.karsei.tobyspringinflearn;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HelloRepositoryJdbc implements HelloRepository {
    private final JdbcTemplate jdbcTemplate;

    public HelloRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Hello findHello(String name) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM hello WHERE name = '" + name + "'", (rs, rowNum) -> new Hello(
                    rs.getString("name"), rs.getInt("count")
            ));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void increaseCount(String name) {
        Hello hello = findHello(name);
        if (hello == null) jdbcTemplate.update("INSERT INTO hello VALUES(?, ?)", name, 1);
        else jdbcTemplate.update("UPDATE hello SET count = ? WHERE name = ?", hello.getCount() + 1, name);
    }
}
