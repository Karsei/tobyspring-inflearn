package kr.pe.karsei.tobyspringinflearn;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class Config {
    // 아래 두 Bean 은 독립 실행형 애플리케이션 방식으로 동작을 하면서 요구된 Bean 들임
    // HelloController, HelloDecorator, HelloService 들은 사용자 구성 정보 / TomcatServletWebServerFactory, DispatcherServlet 들은 자동 구성 정보로 구분함 (Spring 재단에서는)
    // Toby 는 전자를 애플리케이션 로직, 후자를 애플리케이션 인프라스트럭쳐 로직으로 구분
    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }
}
