package kr.pe.karsei.config.autoconfig;

import kr.pe.karsei.config.MyAutoConfiguration;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

@MyAutoConfiguration
public class ServerPropertiesV1Config {
    // @Bean
    public ServerPropertiesV1 serverProperties(Environment environment) {
        // ServerPropertiesV1 properties = new ServerPropertiesV1();

        // 이런 설정들은 이 프로젝트에서 만든 ServerPropertiesV1 가 아닌 라이브러리 상에서의 ServerPropertiesV1 에 상세히 나와있음
        // properties.setContextPath(environment.getProperty("contextPath"));
        // properties.setPort(Integer.parseInt(environment.getProperty("port")));

        // return properties;
        return Binder.get(environment).bind("", ServerPropertiesV1.class).get(); // 일치하는 부분을 ServerPropertiesV1 에 자동으로 넣어준다.
    }
}
