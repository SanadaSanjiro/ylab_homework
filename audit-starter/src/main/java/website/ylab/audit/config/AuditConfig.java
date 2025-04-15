package website.ylab.audit.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import website.ylab.audit.aspects.AuditableAspect;

/**
 * Adds audit advice to a project configuration
 */
@Configuration
public class AuditConfig {
    Logger logger = LogManager.getLogger(AuditConfig.class);
    @Bean
    public AuditableAspect getAspect() {
        logger.info("Audit config applied");
        return new AuditableAspect();
    }
}
