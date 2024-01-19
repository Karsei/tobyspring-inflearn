![environment_abstractions.png](images%2Fenvironment_abstractions.png)

```java
@Bean
ApplicationRunner applicationRunner(Environment environment) {
    return args -> {
        String name = environment.getProperty("my.name");
        System.out.println("My name: " + name);
    };
}
```

위와 같은 코드가 있을 때

## Environment Variables

시스템 환경변수가 보통 먼저 읽어짐. 관용적으로 대문자로 사용

property 에 `.` 이 있는 경우 시스템 환경변수에서는 `_` 가 대체되어 진다.

```
MY_NAME=EnvironmentVaribles
```

## application.properties

Spring Boot 에서 주입하는 경우

```properties
my.name=blah
```

VM 로드 시 아래와 같이 파라미터를 주어서도 가능

```bash
-Dmy.name=blah
```