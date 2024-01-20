package kr.pe.karsei.tobyspringinflearn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@HelloBootTest
//@Rollback(false) // 트랜잭션이 롤백되지 않도록 함
public class JdbcTemplateTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS hello(name varchar(50) primary key, count int)");
    }

    @Test
    void insertAndQuery() {
        jdbcTemplate.update("INSERT INTO hello VALUES(?, ?)", "name", 3);
        jdbcTemplate.update("INSERT INTO hello VALUES(?, ?)", "spring", 1);
        Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM hello", Long.class);
        assertThat(count).isEqualTo(2);
    }

    @Test
    void insertAndQuery2() {
        jdbcTemplate.update("INSERT INTO hello VALUES(?, ?)", "name", 3);
        jdbcTemplate.update("INSERT INTO hello VALUES(?, ?)", "spring2", 1);
        Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM hello", Long.class);
        assertThat(count).isEqualTo(2);
    }
}
