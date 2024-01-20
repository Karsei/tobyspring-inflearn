package kr.pe.karsei.tobyspringinflearn;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE) // 굳이 웹 환경을 구성할 필요 없음
@Transactional
public class HelloRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    HelloRepository helloRepository;

    @Test
    void findHelloFailed() {
        Assertions.assertThat(helloRepository.findHello("Hong")).isNull();
    }

    @Test
    void increaseCount() {
        Assertions.assertThat(helloRepository.countOf("Lee")).isEqualTo(0);

        helloRepository.increaseCount("Lee");
        Assertions.assertThat(helloRepository.countOf("Lee")).isEqualTo(1);

        helloRepository.increaseCount("Lee");
        Assertions.assertThat(helloRepository.countOf("Lee")).isEqualTo(2);
    }
}
