package kr.pe.karsei.config;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyAutoConfigurationSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {
                "kr.pe.karsei.config.autoconfig.DispatcherServletConfig",
                "kr.pe.karsei.config.autoconfig.TomcatWebServerConfig",
        };
    }
}
