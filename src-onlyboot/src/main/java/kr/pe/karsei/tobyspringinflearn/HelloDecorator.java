package kr.pe.karsei.tobyspringinflearn;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary // Bean 2개가 존재하더라도 제일 먼저 고르도록 함
public class HelloDecorator implements HelloService {
    private final HelloService helloService;

    public HelloDecorator(HelloService helloService) {
        this.helloService = helloService;
    }

    @Override
    public String sayHello(String name) {
        return "*" + helloService.sayHello(name) + "*";
    }

    @Override
    public int countOf(String name) {
        return helloService.countOf(name);
    }
}
