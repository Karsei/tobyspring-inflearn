package kr.pe.karsei.tobyspringinflearn;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@MyComponent // @Component 로도 가능
//@Controller // Stereotype(전형적인) 컴포넌트
@RestController // @Controller + @ResponseBody -> 합성 Annotation
public class HelloController {
    private final HelloService service;
//    private final ApplicationContext applicationContext;

    public HelloController(HelloService service) {
        this.service = service;
    }


//    public HelloController(HelloService service,
//                           ApplicationContext applicationContext
//    ) {
//        this.service = service;
//        this.applicationContext = applicationContext;
//
//        System.out.println(applicationContext);
//    }

    @GetMapping("hello")
    // 보통 String 으로 반환하면 해당 문자열의 view 를 찾게 된다.
    // 그대로 뿌려주려면 아래 Annotation 을 붙여야 한다.
    public String hello(String name) {
        //return service.sayHello(Objects.requireNonNull(name));
        if (name == null || name.trim().length() == 0) throw new IllegalArgumentException();
        return service.sayHello(name);
    }

    @GetMapping("count")
    public String count(String name) {
        return name + ": " + service.countOf(name);
    }
}
