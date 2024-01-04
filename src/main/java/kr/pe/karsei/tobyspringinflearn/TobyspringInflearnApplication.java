package kr.pe.karsei.tobyspringinflearn;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class TobyspringInflearnApplication {
    public static void main(String[] args) {
        GenericWebApplicationContext context = new GenericWebApplicationContext();
        // 메타 정보를 넣어주는 방식으로 Bean 등록 (보통 Bean 클래스를 지정해준다) Bean 이 어떻게 구성되어 지는가 등을
        context.registerBean(HelloController.class);
        //context.registerBean(HelloService.class); // 인터페이스는 안됨
        context.registerBean(SimpleHelloService.class);
        // 컨테이너를 초기화 -> Bean 을 만들어줌. 템플릿 메소드 패턴으로 일정한 순서에 의해 작업이 호출되고 서브 클래스에서 확장되는 방법을 통해 확장하도록 만듬
        context.refresh();

        // Tomcat 뿐만 아니라 다른 서버로도 이용 가능하도록 스프링에서 추상화를 지원한다.
        ServletWebServerFactory factory = new TomcatServletWebServerFactory();
        WebServer webServer = factory.getWebServer(servletContext -> {
            // 서블릿을 추가한다고 단순히 서블릿이 동작되지는 않는다. 맵핑을 추가해야 한다.
            servletContext
                    .addServlet("dispatchServlet",
                            // DispatcherServlet 은 GenericWebApplicationContext 를 사용함.
                            // 그러나 아직 어떻게 넘어왔을 때 어떤 핸들러에게 전달해야 하는지는 정해지지 않음
                            // Bean 을 다 뒤져서 웹 요청을 처리할 수 있는 맵핑 정보를 가지고 있는 클래스를 찾는다(@RequestMapping 등) -> 맵핑 테이블을 만들어서 웹 요청이 오면 해당 테이블을 참조해서 연결한다.
                            // 따라서 클래스 레벨에 반드시 맵핑 어노테이션을 붙여야 한다.
                            new DispatcherServlet(context)
                    )
                    // 서블릿 컨테이너가 요청이 들어올 때 어느 서블릿으로 연결할지의 맵핑을 정해주어야 한다.
                    .addMapping("/*");
        });

        // 웹 서버 시작
        webServer.start();
    }
}
