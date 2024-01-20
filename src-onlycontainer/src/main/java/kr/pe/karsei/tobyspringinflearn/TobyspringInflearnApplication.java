package kr.pe.karsei.tobyspringinflearn;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TobyspringInflearnApplication {
    @Bean
    ApplicationRunner run(ConditionEvaluationReport report) {
        return args -> {
            // 알파벳 순으로 깔끔하게 확인 가능. 보고 싶은 것만 확인 가능
            long count = report.getConditionAndOutcomesBySource().entrySet().stream()
                    .filter(co -> co.getValue().isFullMatch())
                    .filter(co -> co.getKey().indexOf("Jmx") < 0)
                    .map(co -> {
                        System.out.println(co.getKey());
                        co.getValue().forEach(c -> {
                            System.out.println("\t" + c.getOutcome()); // 어떤 조건을 통과했는가 볼 수 있음
                        });
                        System.out.println();
                        return co;
                    })
                    .count();
            System.out.println("total: " + count);
        };
    }

    public static void main(String[] args) {
        // JVM Option 에 `-Ddebug` 붙이면 상세 내용 볼 수 있음
        SpringApplication.run(TobyspringInflearnApplication.class, args);
    }
}
