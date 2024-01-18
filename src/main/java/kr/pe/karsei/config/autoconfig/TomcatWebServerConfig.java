package kr.pe.karsei.config.autoconfig;

import kr.pe.karsei.config.ConditionalMyOnClass;
import kr.pe.karsei.config.MyAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

@MyAutoConfiguration
//@Configuration
//@Conditional(TomcatWebServerConfig.TomcatCondition.class) // Condition 인터페이스를 참조함
@ConditionalMyOnClass("org.apache.catalina.startup.Tomcat")
public class TomcatWebServerConfig {
    @Bean("tomcatWebServerFactory")
    @ConditionalOnMissingBean // 사용자가 등록한 Bean 이 없으면 해당 Bean 을 등록한다.
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

//    static class TomcatCondition implements Condition {
//        @Override
//        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
//            return false;
//        }
//    }
}
