package kr.pe.karsei.tobyspringinflearn;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

import java.io.IOException;

public class TobyspringInflearnApplication {
    public static void main(String[] args) {
        // Tomcat 뿐만 아니라 다른 서버로도 이용 가능하도록 스프링에서 추상화를 지원한다.
        ServletWebServerFactory factory = new TomcatServletWebServerFactory();
        WebServer webServer = factory.getWebServer(servletContext -> {
            // 서블릿을 추가한다고 단순히 서블릿이 동작되지는 않는다. 맵핑을 추가해야 한다.
            servletContext.addServlet("hello", new HttpServlet() {
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    super.service(req, resp);
                    resp.setStatus(200);
                    resp.setHeader("Content-Type", "test/plain");
                    resp.getWriter().println("Hello Servlet");
                }
            })
                    // 서블릿 컨테이너가 요청이 들어올 때 어느 서블릿으로 연결할지의 맵핑을 정해주어야 한다.
                    .addMapping("/hello");
        });

        // 웹 서버 시작
        webServer.start();
    }
}
