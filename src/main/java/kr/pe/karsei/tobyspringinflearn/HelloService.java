package kr.pe.karsei.tobyspringinflearn;

public interface HelloService {
    String sayHello(String name);

    default int countOf(String name) {
        return 0;
    }
}
