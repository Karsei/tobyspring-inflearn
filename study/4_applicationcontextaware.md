```java
public class MySpringApplication {
    public static void run(Class<?> applicationClass, String... args) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();
                
                ServletWebServerFactory factory = this.getBean(ServletWebServerFactory.class);
                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
                //dispatcherServlet.setApplicationContext(this);
```

위에서 `setApplicationContext` 를 지정해주지 않아도 애플리케이션을 실행하면 알아서 주입이 되어 있다.  그 이유는 `DispatcherServlet` 이 `ApplicationContextAware` 인터페이스를 구현한 클래스이기 때문이다.

`ApplicationContextAware` 인터페이스를 구현한 클래스는 Spring 에서 나중에 Bean 등록 후 `ApplicationContext` 를 추가적으로 주입해준다.

```java
@RestController
public class HelloController implements ApplicationContextAware {
    private final HelloService service;
    private ApplicationContext applicationContext;
    
    public HelloController(HelloService service) {
        this.service = service;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println(applicationContext);
    }
```

위와 같이 작성하면 정상적으로 주입되는 모습을 확인할 수 있다.

최신 방법으로 굳이 인터페이스를 붙이지 않아도 `ApplicationContext` 를 생성자를 통해 이용하는 방법이 좋아 보인다.


```java
@RestController
public class HelloController {
    private final HelloService service;
    private final ApplicationContext applicationContext;
    
    public HelloController(HelloService service,
                           ApplicationContext applicationContext
    ) {
        this.service = service;
        this.applicationContext = applicationContext;

        System.out.println(applicationContext);
    }
```