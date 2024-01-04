package kr.pe.karsei.tobyspringinflearn;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@RequestMapping("/hello")
public class HelloController {
    private final HelloService service;

    public HelloController(HelloService service) {
        this.service = service;
    }

    @GetMapping
    // 보통 String 으로 반환하면 해당 문자열의 view 를 찾게 된다.
    // 그대로 뿌려주려면 아래 Annotation 을 붙여야 한다.
    @ResponseBody
    public String hello(String name) {
        return service.sayHello(Objects.requireNonNull(name));
    }
}
