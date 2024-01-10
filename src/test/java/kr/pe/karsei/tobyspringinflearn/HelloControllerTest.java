package kr.pe.karsei.tobyspringinflearn;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HelloControllerTest {
    @Test
    void controllerTest() {
        HelloController controller = new HelloController(name -> name);
        String result = controller.hello("Spring");

        Assertions.assertThat(result).isEqualTo("Spring");
    }

    @Test
    void controllerTestWithFailure() {
        HelloController controller = new HelloController(name -> name);

        Assertions.assertThatThrownBy(() -> controller.hello(null))
                .isInstanceOf(IllegalArgumentException.class);

        Assertions.assertThatThrownBy(() -> controller.hello(""))
                .isInstanceOf(IllegalArgumentException.class);
    }
}