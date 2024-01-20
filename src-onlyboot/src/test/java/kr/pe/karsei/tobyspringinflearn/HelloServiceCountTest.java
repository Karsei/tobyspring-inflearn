package kr.pe.karsei.tobyspringinflearn;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.IntStream;

@HelloBootTest
public class HelloServiceCountTest {
    @Autowired
    HelloService helloService;
    @Autowired
    HelloRepository helloRepository;

    @Test
    void sayHelloIncreaseCount() {
        IntStream.rangeClosed(1, 10).forEach(count -> {
            helloService.sayHello("Lee");
            Assertions.assertThat(helloRepository.countOf("Lee")).isEqualTo(count);
        });
    }
}
