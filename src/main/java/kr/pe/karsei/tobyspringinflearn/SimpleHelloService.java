package kr.pe.karsei.tobyspringinflearn;

import org.springframework.stereotype.Service;

//@Component
@Service
public class SimpleHelloService implements HelloService {
    private final HelloRepository helloRepository;

    public SimpleHelloService(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }

    @Override
    public String sayHello(String name) {
        helloRepository.increaseCount(name);
        return "Hello " + name;
    }

    @Override
    public int countOf(String name) {
        return helloRepository.countOf(name);
    }
}
