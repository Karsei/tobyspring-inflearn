# @Conditional

> Spring Framework 에서 지원하는 `@Profile` 도 `@Conditional` Annotation 이다.

## Class Conditions

* `@ConditionalOnClass`
* `@ConditionalOnMissingClass`

클래스 내의 존재를 확인해서 포함 여부를 결정한다.

주로 `@Condiguration` 클래스 레벨에서 사용하지만, `@Bean` 메소드에도 적용 가능하다. 단, 클래스 레벨의 검증 없이 `@Bean` 메소드에만 적용하면 불필요하게 `@Configuration` 클래스가 Bean 으로 등록되기 때문에 클래스 레벨 사용을 우선해야 한다.

## Bean Conditions

* `@ConditionalOnBean`
* `@ConditionalOnMissingBean`

Bean 의 존재 여부를 기준으로 포함 여부를 결정한다.

Bean 의 타입 또는 이름을 지정할 수 있으며, 지정된 Bean 정보가 없으면 메소드의 리턴 타입을 기준으로 Bean 존재 여부를 체크한다.

컨테이너에 등록된 Bean 정보를 기준으로 체크하므로 자동 구성 사이에 적용하려면 `@Configuration` 클래스의 적용 순서가 중요하다. 개발자가 직접 정의한 Custom Bean 구성 정보가 자동 구성 정보 처리보다 우선하기 때문에 이 관계에 적용하는 것은 안전하다. 반대로, Custom Bean 구성 정보에 적용하는 것은 피해야 한다.

> `@Configuration` 클래스 레벨의 `@ConditionalOnClass` 와 `@Bean` 메소드 레벨의 `@ConditionalOnMissingBean` 조합은 가장 대표적으로 사용되는 방식이다.

## Property Conditions

* `@ConditionalOnProperty` - Spring 의 환경 Property 정보를 이용한다.

## Resource Conditions

* `@ConditionalOnResource` - 지정된 리소스(파일)의 존재를 확인한다.

## Web Application Conditions

* `@ConditionalOnWebApplication`
* `@ConditionalOnNotWebApplcation`

웹 애플리케이션 여부를 확인한다. (모든 Spring Boot 프로젝트가 웹 기술을 사용해야 하는 것은 아니다.)

## SpEL Expression Conditions

* `@ConditionalOnExpression`

Spring SpEL (Spring 표현식)의 처리 결과를 기준으로 판단한다. 상세한 조건 설정이 가능하다.