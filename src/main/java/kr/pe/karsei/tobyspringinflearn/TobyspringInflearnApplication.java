package kr.pe.karsei.tobyspringinflearn;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@MySpringBootApplication // @Configuration + @ComponentScan -> 합성 Annotation
//@Configuration
//@ComponentScan // 이 클래스가 있는 패키지부터 시작해서 하위 패키지 찾아서 @Component 가 붙은 것을 찾아내고 알아서 등록해줌
public class TobyspringInflearnApplication {
    // 아래는 Config.java 로 이동됨
//    @Bean
//    public ServletWebServerFactory servletWebServerFactory() {
//        return new TomcatServletWebServerFactory();
//    }
//
//    @Bean
//    public DispatcherServlet dispatcherServlet() {
//        return new DispatcherServlet();
//    }

    public static void main(String[] args) {
        SpringApplication.run(TobyspringInflearnApplication.class, args);
        // MySpringApplication.run(TobyspringInflearnApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(Environment environment) {
        return args -> {
            String name = environment.getProperty("my.name");
            System.out.println("My name: " + name);
        };
    }
}
