package kr.pe.karsei.config.autoconfig;

import kr.pe.karsei.config.ConditionalMyOnClass;
import kr.pe.karsei.config.EnableMyConfigurationProperties;
import kr.pe.karsei.config.MyAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.*;

@MyAutoConfiguration
//@Configuration
//@Conditional(TomcatWebServerConfig.TomcatCondition.class) // Condition 인터페이스를 참조함
@ConditionalMyOnClass("org.apache.catalina.startup.Tomcat")
//@Import(ServerPropertiesV2.class)
@EnableMyConfigurationProperties(ServerPropertiesV2.class)
public class TomcatWebServerConfig {
    // ServerPropertiesV1Config 에서 담당하도록 변경
    // @Value("${contextPath:}")
    // String contextPath;
    // @Value("${port:8080}") // 기본값은 : 을 붙인다. 없으면 :를 붙이고 그냥 비우면 된다.
    // int port;

    /*
    @Bean("tomcatWebServerFactory")
    @ConditionalOnMissingBean // 사용자가 등록한 Bean 이 없으면 해당 Bean 을 등록한다.
    public ServletWebServerFactory servletWebServerFactoryV1(Environment environment,
                                                             ServerPropertiesV1 serverPropertiesV1) {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

        // System.out.println(contextPath);

        // factory.setContextPath(environment.getProperty("contextPath"));
        // factory.setContextPath(contextPath); // http://localhost/app/hello 와 같이 맨 앞에 /app 이 붙는다.

        // factory.setPort(port);

        factory.setContextPath(serverPropertiesV1.getContextPath());
        factory.setPort(serverPropertiesV1.getPort());

        return factory;
    }
    */

    @Bean("tomcatWebServerFactory")
    @ConditionalOnMissingBean
    public ServletWebServerFactory servletWebServerFactoryV2(ServerPropertiesV2 serverPropertiesV2) {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

        factory.setContextPath(serverPropertiesV2.getContextPath());
        factory.setPort(serverPropertiesV2.getPort());

        return factory;
    }

//    static class TomcatCondition implements Condition {
//        @Override
//        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
//            return false;
//        }
//    }
}
