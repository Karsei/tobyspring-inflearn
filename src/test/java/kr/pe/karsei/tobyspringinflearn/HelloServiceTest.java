package kr.pe.karsei.tobyspringinflearn;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloServiceTest {
    @Test
    void sayHello() {
        SimpleHelloService service = new SimpleHelloService();
        String res = service.sayHello("Test");

        Assertions.assertThat(res).isEqualTo("Hello Test");
    }
}