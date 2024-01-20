![springboot_all_flow.png](images%2Fspringboot_all_flow.png)

![springboot_autoconfiguration_analize_solutions.png](images%2Fspringboot_autoconfiguration_analize_solutions.png)

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