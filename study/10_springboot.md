![springboot_all_flow.png](images%2Fspringboot_all_flow.png)

![springboot_autoconfiguration_analize_solutions.png](images%2Fspringboot_autoconfiguration_analize_solutions.png)

https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#documentation

# Spring Boot 자동 구성 분석 방법

* `-Ddebug, --debug`
  * Spring Boot 가 imports 파일로 불러온 자동 구성 후보 중에서 Conditional 로 통과한 Beans 들이 어떤 것들이 있는지 자세히 보여줌. 선택되지 않은 것들도 무슨 이유로 안되었는지 전부 출력됨
  * 단, 자동 구성이 140개가 넘고 너무 자세하게 나와서 보기는 힘듬
* `ConditionEvaluationReport`
  * 위 `--debug` 로는 보기가 힘드니 직접 코드를 작성 후 자동 구성 Conditional 결과를 보는게 나음. 즉, 원하는 것만 보는 필터링이 가능
* `ListableBeanFactory`
  * `ApplicationContext` 안에 있음. 
  * Spring 컨테이너 내 어떤 Bean 들이 등록되었는지 확인 가능
* Spring Boot Reference
  * 모든 것을 설명해주진 않지만 중요한 내용을 정리해서 주제에 맞는 내용과 예제까지 들어가 있는 경우가 많음
  * 소스 코드와 javadocs, 주석을 보면 중요한 내용이 많음
* 자동 구성 클래스와 조건, Bean 확인
* Property 클래스와 바인딩
  * `Customizer`
    * 어떤 Object 의 기본 설정을 바꿀 수 있는 그 기능을 변경하는 책임만 담당하는 클래스를 만들어서 주입하는 기능
  * `Configurer`
    * Spring Framework 과 관련된 주요한 기능 설정을 하나로 모아 놓아 하나의 Object 로 관리할 수 있도록 만들어진 인터페이스
    * 상당히 직관적이므로 어렵지는 않음

살펴 보다가 Spring Framework 기술 뿐만 아니라 연관된 것까지 확인하면 좋음

뭔가 라이브러리를 추가했을 때나 다른 적용 가능한 관련 기술이 뭐가 있을까? 도 고민해보면 좋음. 이런 경우에 장점이 뭐가 있고 이런 곳에 적용하면 어떤게 좋은지 등

# `Core` 자동 구성

```java
@AutoConfiguration
@ConditionalOnProperty(prefix = "spring.aop", name = "auto", havingValue = "true", matchIfMissing = true)
public class AopAutoConfiguration {
    ...

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnMissingClass("org.aspectj.weaver.Advice")
    @ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "true",
            matchIfMissing = true)
    static class ClassProxyingConfiguration {
        @Bean
        static BeanFactoryPostProcessor forceAutoProxyCreatorToUseClassProxying() {
            return (beanFactory) -> {
                if (beanFactory instanceof BeanDefinitionRegistry registry) {
                    AopConfigUtils.registerAutoProxyCreatorIfNecessary(registry);
                    AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);
                }
            };
        }
    }
}
```

우리가 property 를 직접 정의하지 않았는데도 매칭되는 이유는 `matchIfMissing` 속성 때문

`org.aspectj.weaver.Advice` 클래스가 존재하지 않으므로 위 `ClassProxyingConfiguration` 는 자동 구성에 포함되어짐

Async 구성에 많이 사용되는 `TaskExecutionAutoConfiguration` 을 보면 빌더 패턴이 Bean 으로 등록되어 사용하는 것을 볼 수 있는데 나중에 따로 빌더 Bean 을 이용해서 별개로 커스텀하게 만들어 줄 수 도 있음

```java
@ConditionalOnClass(ThreadPoolTaskExecutor.class)
@AutoConfiguration
@EnableConfigurationProperties(TaskExecutionProperties.class)
@Import({ TaskExecutorConfigurations.ThreadPoolTaskExecutorBuilderConfiguration.class,
		TaskExecutorConfigurations.TaskExecutorBuilderConfiguration.class,
		TaskExecutorConfigurations.SimpleAsyncTaskExecutorBuilderConfiguration.class,
		TaskExecutorConfigurations.TaskExecutorConfiguration.class })
public class TaskExecutionAutoConfiguration {
```

```java
class TaskExecutorConfigurations {
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnMissingBean(Executor.class)
    @SuppressWarnings("removal")
    static class TaskExecutorConfiguration {
        @Lazy
        @Bean(name = {TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME,
                AsyncAnnotationBeanPostProcessor.DEFAULT_TASK_EXECUTOR_BEAN_NAME})
        @ConditionalOnThreading(Threading.PLATFORM)
        ThreadPoolTaskExecutor applicationTaskExecutor(TaskExecutorBuilder taskExecutorBuilder,
                                                       ObjectProvider<ThreadPoolTaskExecutorBuilder> threadPoolTaskExecutorBuilderProvider) {
          ...
        }
    }
    ...

    @Configuration(proxyBeanMethods = false)
    @SuppressWarnings("removal")
    static class TaskExecutorBuilderConfiguration {
        @Bean
        @ConditionalOnMissingBean
        @Deprecated(since = "3.2.0", forRemoval = true)
        TaskExecutorBuilder taskExecutorBuilder(TaskExecutionProperties properties,
                                                ObjectProvider<TaskExecutorCustomizer> taskExecutorCustomizers,
                                                ObjectProvider<TaskDecorator> taskDecorator) {
            TaskExecutionProperties.Pool pool = properties.getPool();
            TaskExecutorBuilder builder = new TaskExecutorBuilder();
            builder = builder.queueCapacity(pool.getQueueCapacity());
            builder = builder.corePoolSize(pool.getCoreSize());
            builder = builder.maxPoolSize(pool.getMaxSize());
            builder = builder.allowCoreThreadTimeOut(pool.isAllowCoreThreadTimeout());
            builder = builder.keepAlive(pool.getKeepAlive());
            TaskExecutionProperties.Shutdown shutdown = properties.getShutdown();
            builder = builder.awaitTermination(shutdown.isAwaitTermination());
            builder = builder.awaitTerminationPeriod(shutdown.getAwaitTerminationPeriod());
            builder = builder.threadNamePrefix(properties.getThreadNamePrefix());
            builder = builder.customizers(taskExecutorCustomizers.orderedStream()::iterator);
            builder = builder.taskDecorator(taskDecorator.getIfUnique());
            return builder;
        }
    }
}
```

property 에 무엇이 있는지 알아보려면 관련 property 클래스를 찾아가면 됨

기술을 사용하기 전에 **기본값이 무엇인지 미리 알아보는 것이 좋음**

```java
@ConfigurationProperties("spring.task.execution")
public class TaskExecutionProperties {
    private final Pool pool = new Pool();
    ...
}
```

# `Web` 자동 구성

살펴보기 전에 IDE 에서 제공하는 dependency 구조를 보면 어떤 의존 라이브러리들이 사용되고 있는지 확인 가능

![ide_gradle_dependencies_web.png](images%2Fide_gradle_dependencies_web.png)

* JacksonAutoConfiguration
* RestTemplateAutoConfiguration

Builder 패턴도 제공하고 있음

* EmbeddedWebServerFactoryFactoryCustomizerAutoConfiguration
* DispatcherServletAutoConfiguration

# `Jdbc` 자동 구성

* DataSourceAutoConfiguration
* DataSourceTransactionManager
* SqlInitializationAutoConfiguration
* TransactionAutoConfiguration
  * PlatformTransactionManager
  * TransactionTemplateConfiguration

# 정리

## 목적

* Spring Boot 가 Spring 기술을 어떻게 활용하는지 배우고 응용할 수 있다.
* Spring Boot 가 선택한 기술, 자동으로 만들어주는 구성, Default 설정이 어떤 것인지 확인할 수 있다.
* 필요할 때 Boot 의 기본 구성을 수정하거나 확장할 수 있다.
* 나만의 Spring Boot 모듈을 만들어 활용할 수 있다.

## Spring Boot

Spring Framework 를 잘 쓰게 도와주는 도구의 모음

* Servlet 컨테이너와 관련된 모든 번거로운 작업을 감춰줌
* Spring 과 각종 기술의 주요 인프라스트럭처 Bean 을 자동 구성을 이용해서 자동으로 등록해줌
* 외부 설정, Custom Bean 등록을 통해 유연하게 확장 가능

## Spring Framework

* Bean 오브젝트의 생명 주기를 관리하는 컨테이너
* Bean 오브젝트의 의존 관계를 동적으로 주입해주는 Assembler
  * 런타임에 동적으로 컨테이너에 의해서 관계가 주입됨. Dependency Injection Assembler 역할을 해줌
* 구성 정보(Configuration Metadata)와 애플리케이션 기능을 담은 오브젝트가 결합되어 동작하는 애플리케이션이 된다.
* `@Configuration`, `@Bean`, `@Import` 를 이용한 구성 정보
* 메타 애노테이션, 합성 애노테이션

## 더 알아가야 할 주제들

* Spring Boot Core (Profile, Logging, Testing, ...)
* 핵심 기술 영역 (Web, Data, Messaging, IO, ...)
* 운영 환경의 모니터링, 관리 방법
  * production ready feature
* 컨테이너, 배포, 빌드 툴
* Spring Boot 3.x
* Spring Framework 와 Java 표준, 오픈소스 기술

## Spring Boot 3.0

* Spring 6
* JDK 17 또는 그 이상
* Jakarta EE 9, 10