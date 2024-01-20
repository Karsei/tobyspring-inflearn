package kr.pe.karsei.tobyspringinflearn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@JdbcTest // Console 에 Replacing 'dataSource' DataSource bean with embedded version 나타남 -> 빠르게 테스트 가능하나 주의해야 함
public class DataSourceTest {
    @Autowired
    DataSource dataSource;

    @Test
    void connect() throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.close();
    }
}
