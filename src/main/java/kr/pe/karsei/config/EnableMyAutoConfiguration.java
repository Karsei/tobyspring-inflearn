package kr.pe.karsei.config;

import kr.pe.karsei.config.autoconfig.DispatcherServletConfig;
import kr.pe.karsei.config.autoconfig.TomcatWebServerConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
//@Import({DispatcherServletConfig.class, TomcatWebServerConfig.class})
@Import(MyAutoConfigurationSelector.class)
public @interface EnableMyAutoConfiguration {
}
