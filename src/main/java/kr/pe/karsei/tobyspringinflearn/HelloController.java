package kr.pe.karsei.tobyspringinflearn;

import java.util.Objects;

public class HelloController {
    private final HelloService service;

    public HelloController(HelloService service) {
        this.service = service;
    }

    public String hello(String name) {
        return service.sayHello(Objects.requireNonNull(name));
    }
}
