package kr.pe.karsei.tobyspringinflearn;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

public class TobyspringInflearnApplication {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        // 메타 정보를 넣어주는 방식으로 Bean 등록 (보통 Bean 클래스를 지정해준다) Bean 이 어떻게 구성되어 지는가 등을
        context.registerBean(HelloController.class);
        // 컨테이너를 초기화 -> Bean 을 만들어줌
        context.refresh();

        // Tomcat 뿐만 아니라 다른 서버로도 이용 가능하도록 스프링에서 추상화를 지원한다.
        ServletWebServerFactory factory = new TomcatServletWebServerFactory();
        WebServer webServer = factory.getWebServer(servletContext -> {
            // 서블릿을 추가한다고 단순히 서블릿이 동작되지는 않는다. 맵핑을 추가해야 한다.
            servletContext
                    .addServlet("frontController", new HttpServlet() {
                        @Override
                        protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                            if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
                                String name = req.getParameter("name");

                                HelloController helloController = context.getBean(HelloController.class);
                                String returnedName = helloController.hello(name);

                                // resp.setStatus(HttpStatus.OK.value()); // 에러가 나지 않는한 서블릿 컨테이너가 기본적으로 200 전달
                                // resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
                                resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
                                resp.getWriter().println("Hello Servlet: %s".formatted(returnedName));
                            }
                            else {
                                resp.setStatus(HttpStatus.NOT_FOUND.value());
                            }
                        }
                    })
                    // 서블릿 컨테이너가 요청이 들어올 때 어느 서블릿으로 연결할지의 맵핑을 정해주어야 한다.
                    .addMapping("/*");
        });

        // 웹 서버 시작
        webServer.start();
    }
}
