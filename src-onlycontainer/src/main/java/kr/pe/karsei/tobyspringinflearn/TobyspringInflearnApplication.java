package kr.pe.karsei.tobyspringinflearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TobyspringInflearnApplication {
    public static void main(String[] args) {
        // JVM Option 에 `-Ddebug` 붙이면 상세 내용 볼 수 있음
        SpringApplication.run(TobyspringInflearnApplication.class, args);
    }
}
