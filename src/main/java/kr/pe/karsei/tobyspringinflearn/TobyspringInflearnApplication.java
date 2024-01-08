package kr.pe.karsei.tobyspringinflearn;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ComponentScan // 이 클래스가 있는 패키지부터 시작해서 하위 패키지 찾아서 @Component 가 붙은 것을 찾아내고 알아서 등록해줌
public class TobyspringInflearnApplication {
    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        // Spring 컨테이너 안에서 서블릿이 동작되도록 함
        // GenericWebApplicationContext 에서 AnnotationConfigWebApplicationContext 로 변경
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh(); // 생략하면 안됨

                // Tomcat 뿐만 아니라 다른 서버로도 이용 가능하도록 스프링에서 추상화를 지원한다.
                ServletWebServerFactory factory = this.getBean(ServletWebServerFactory.class);
                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
                // ApplicationContextAware 인터페이스를 이용해서 만들어진 Bean 은 나중에 나중에 팩토리 메서드에서 만들어지든 상관없이 컨테이너에 등록된 후에 컨테이너는 인터페이스의 setter 메소드(applicationContext)를 이용하여 주입한다.
                // ApplicationContextAware 는 Spring 이 초기화되는 과정에서 호출된다.
                dispatcherServlet.setApplicationContext(this);

                WebServer webServer = factory.getWebServer(servletContext -> {
                    // 서블릿을 추가한다고 단순히 서블릿이 동작되지는 않는다. 맵핑을 추가해야 한다.
                    servletContext
                            .addServlet("dispatchServlet",
                                    // 어떻게 넘어왔을 때 어떤 핸들러에게 전달해야 하는지는 정해지지 않음
                                    // Bean 을 다 뒤져서 웹 요청을 처리할 수 있는 맵핑 정보를 가지고 있는 클래스를 찾는다(@RequestMapping 등) -> 맵핑 테이블을 만들어서 웹 요청이 오면 해당 테이블을 참조해서 연결한다.
                                    // 따라서 클래스 레벨에 반드시 맵핑 어노테이션을 붙여야 한다.
                                    dispatcherServlet
                            )
                            // 서블릿 컨테이너가 요청이 들어올 때 어느 서블릿으로 연결할지의 맵핑을 정해주어야 한다.
                            .addMapping("/*");
                });

                // 웹 서버 시작
                webServer.start();
            }
        };
        // 이 구성 정보를 담긴 클래스를 등록하도록 해주어야 한다. -> Bean 생성됨
        context.register(TobyspringInflearnApplication.class);
        // 컨테이너를 초기화 -> Bean 을 만들어줌. 템플릿 메소드 패턴으로 일정한 순서에 의해 작업이 호출되고 서브 클래스에서 확장되는 방법을 통해 확장하도록 만듬
        context.refresh();
    }
}
