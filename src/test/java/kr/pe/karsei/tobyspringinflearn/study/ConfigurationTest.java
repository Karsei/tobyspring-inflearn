package kr.pe.karsei.tobyspringinflearn.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 학습 테스트 작성
public class ConfigurationTest {
    // 단순히 Java 코드 상으로 보았을 때 common 은 각각 생성되어 총 2번 호출된다.
    // 생성된 것도 서로 다르다.
    @Test
    void configuration() {
        Common common = new Common();
        Assertions.assertThat(new Common()).isNotSameAs(new Common());
        Assertions.assertThat(common).isSameAs(common);

        MyConfig myConfig = new MyConfig();
        Bean1 bean1 = myConfig.bean1();
        Bean2 bean2 = myConfig.bean2();
        Assertions.assertThat(bean1.common).isNotSameAs(bean2.common);
    }

    // Spring 에서는 동일하다고 나옴 - Configuration 때문에 그런 것
    @Test
    void configurationWithContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MyConfig.class);
        context.refresh();

        Bean1 bean1 = context.getBean(Bean1.class);
        Bean2 bean2 = context.getBean(Bean2.class);
        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }

    static class MyConfigProxy extends MyConfig {
        private Common common;

        @Override
        Common common() {
            if (this.common == null) this.common = super.common();
            return this.common;
        }
    }

    // Configuration 의 proxyBeanMethods 방식 - spring 5.2 에서 나온 기능
    // 여러 번 호출하더라도 딱 하나만 사용하도록 함
    @Test
    void configurationWithContextProxy() {
        MyConfigProxy myConfigProxy = new MyConfigProxy();
        Bean1 bean1 = myConfigProxy.bean1();
        Bean2 bean2 = myConfigProxy.bean2();
        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }

    @Configuration
    static class MyConfig {
        @Bean
        Common common() {
            return new Common();
        }

        @Bean
        Bean1 bean1() {
            return new Bean1(common());
        }

        @Bean
        Bean2 bean2() {
            return new Bean2(common());
        }
    }

    static class Bean1 {
        private final Common common;

        public Bean1(Common common) {
            this.common = common;
        }
    }

    static class Bean2 {
        private final Common common;

        public Bean2(Common common) {
            this.common = common;
        }
    }

    static class Common {
    }
}
