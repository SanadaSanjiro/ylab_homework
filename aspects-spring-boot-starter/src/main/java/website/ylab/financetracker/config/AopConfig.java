package website.ylab.financetracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import website.ylab.financetracker.aspects.LoggableAspect;

@Configuration
public class AopConfig {
    @Bean
    public LoggableAspect getLoggableAspect() {
        System.out.println("Aspect config called");
        return new LoggableAspect();
    }
}
