package kr.pe.karsei.tobyspringinflearn;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.jupiter.api.Assertions.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@UnitTest // Meta Annotation 으로 쓰려면 대상 Annotation Type 에 ANNOTATION_TYPE 이 있어야 한다.
@interface FastUnitTest {

}

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Test
@interface UnitTest {

}

class HelloServiceTest {
    @FastUnitTest
    void sayHello() {
        SimpleHelloService service = new SimpleHelloService();
        String res = service.sayHello("Test");

        Assertions.assertThat(res).isEqualTo("Hello Test");
    }

    @Test
    void helloDecoratorTest() {
        HelloDecorator decorator = new HelloDecorator(name -> name);
        String res = decorator.sayHello("Test");

        Assertions.assertThat(res).isEqualTo("*Test*");
    }
}