package kr.pe.karsei.tobyspringinflearn;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith(SpringExtension.class) // Spring Context 를 이용한 컨테이너 테스트 가능
@ContextConfiguration(classes = TobyspringInflearnApplication.class) // 모든 bEan 구성 정보를 끌어오는 시작점이 되는 클래스 -> Import, ComponentScan 을 통한 Bean 구성 가져오기 가능
@TestPropertySource("classpath:/application.properties") // application.properties 등록은 Spring Framework 가 아닌 Spring Boot 에서 동작되는 것이므로 따로 테스트에서 불러오도록 추가해야 함
@Transactional
public @interface HelloBootTest {
}
