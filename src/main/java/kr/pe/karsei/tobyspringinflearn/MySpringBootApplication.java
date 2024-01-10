package kr.pe.karsei.tobyspringinflearn;

import kr.pe.karsei.config.EnableMyAutoConfiguration;
import kr.pe.karsei.config.autoconfig.DispatcherServletConfig;
import kr.pe.karsei.config.autoconfig.TomcatWebServerConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 원래 Java Default 는 클래스임 -> 로드가 될 때에는 클래스가 사라짐 그래서 Runtime 으로 올림
@Target(ElementType.TYPE)
@Configuration
@ComponentScan
//@Import({DispatcherServletConfig.class, TomcatWebServerConfig.class})
@Import(EnableMyAutoConfiguration.class) // ComponentScan 이 안되므로 직접 Import 진행
public @interface MySpringBootApplication {
}
