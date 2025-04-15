package website.ylab.aspects;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Adds logging advice to a project configuration
 */
@Configuration
@Log4j2
public class AopConfig {
    @Bean
    public LoggableAspect getLoggableAspect() {
        log.info("Execution time logging aspect configuration applied");
        return new LoggableAspect();
    }
}
