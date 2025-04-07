package website.ylab.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Adds logging advice to a project configuration
 */
@Configuration
public class AopConfig {
    Logger logger = LogManager.getLogger(AopConfig.class);

    @Bean
    public LoggableAspect getLoggableAspect() {
        logger.info("Execution time logging aspect configuration applied");
        return new LoggableAspect();
    }
}
