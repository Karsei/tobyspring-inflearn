인프런 `토비` 님의 `스프링 부트 이해와 원리` [강의](https://www.inflearn.com/course/%ED%86%A0%EB%B9%84-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EC%9D%B4%ED%95%B4%EC%99%80%EC%9B%90%EB%A6%AC)를 들으면서 진행한 프로젝트입니다.

* 독립 실행형 Servlet Application
  * Containerless, Opinionated
  * Servlet 등록, 요청 처리
  * Front Controller, Mapping, Binding
* 독립 실행형 Spring Application
  * Dependency Injection 적용
  * Dispatcher Servlet 전환
  * Spring Container 통합
  * @ComponentScan
  * @SpringBootApplication
* DI, 테스트, 디자인 패턴
  * DI와 단위 테스트
  * DI를 이용한 Decorator, Proxy 패턴
* 자동 구성 기반 애플리케이션
  * 메타 어노테이션과 합성 어노테이션
  * Bean 오브젝트의 역할과 구분 (사용자 구성 정보, 자동 구성 정보) - AutoConfiguration 관련
    * 사용자 구성 정보 - HelloController, HelloDecorator, ...
    * 자동 구성 정보 - TomcatServletWebServerFactory, DispatcherSerlvet, ...
    * 자동 구성 정보 파일 분리 및 어노테이션 적용
  * `@Configuration`과 proxyBeanMethods
* 조건부 자동 구성
  * @Conditional, Condition
* 외부 설정을 이용한 자동 구성
  * Environment 추상화와 Property
  * `@Value`, `PropertySourcesPlaceholderConfigurer`
  * Property Bean 의 후처리기 도입
* Spring JDBC 자동 구성 개발
  * DataSource 자동 구성 클래스 및 JdbcTemplate, TransactionManager 구성
* Spring Boot 자세히 살펴보기