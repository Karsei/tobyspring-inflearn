인프런 `토비` 님의 `스프링 부트 이해와 원리` 강의를 들으면서 진행한 프로젝트입니다.

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