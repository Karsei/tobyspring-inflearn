package kr.pe.karsei.tobyspringinflearn;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 어디까지 유지될 것인가
@Target(ElementType.TYPE) // 적용 대상
@Component // Meta Annotation
public @interface MyComponent {
}
