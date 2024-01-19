package kr.pe.karsei.config.autoconfig;

import kr.pe.karsei.config.MyAutoConfiguration;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@MyAutoConfiguration
public class ServerPropertiesConfig {
    @Bean
    public ServerProperties serverProperties(Environment environment) {
        ServerProperties properties = new ServerProperties();

        // 이런 설정들은 이 프로젝트에서 만든 ServerProperties 가 아닌 라이브러리 상에서의 ServerProperties 에 상세히 나와있음
        properties.setContextPath(environment.getProperty("contextPath"));
        properties.setPort(Integer.parseInt(environment.getProperty("port")));

        // return properties;
        return Binder.get(environment).bind("", ServerProperties.class).get(); // 일치하는 부분을 ServerProperties 에 자동으로 넣어준다.
    }
}
