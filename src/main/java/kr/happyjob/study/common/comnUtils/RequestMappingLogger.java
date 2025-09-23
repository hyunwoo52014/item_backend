package kr.happyjob.study.common.comnUtils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
public class RequestMappingLogger {

    private final ApplicationContext applicationContext;

    private final Logger logger = LogManager.getLogger(this.getClass());

    public RequestMappingLogger(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public CommandLineRunner logRequestMappings() {
        return args -> {
            RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
            mapping.getHandlerMethods().forEach((key, value) -> {
                logger.info("Mapping: " + key + " -> " + value);
            });
        };
    }
}
