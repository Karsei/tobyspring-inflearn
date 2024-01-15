package kr.pe.karsei.config;

import org.springframework.context.annotation.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Configuration(
        proxyBeanMethods = false // ConfigurationTest.java 참조
)
public @interface MyAutoConfiguration {
}
