package kr.pe.karsei.config;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.stream.StreamSupport;

public class MyAutoConfigurationSelector implements DeferredImportSelector {
    private final ClassLoader classLoader;

    // BeanClassLoaderAware interface 또는 생성자 주입으로 사용
    public MyAutoConfigurationSelector(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // ImportCandidates.load 를 보면 META-INF/spring/ 안에서 불러온다고 나와 있음
        Iterable<String> candidates = ImportCandidates.load(MyAutoConfiguration.class, classLoader);
        return StreamSupport.stream(candidates.spliterator(), false)
                .toArray(String[]::new);

        // return new String[] {
        //         "kr.pe.karsei.config.autoconfig.DispatcherServletConfig",
        //         "kr.pe.karsei.config.autoconfig.TomcatWebServerConfig",
        // };
    }
}
