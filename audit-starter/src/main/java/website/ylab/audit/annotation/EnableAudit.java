package website.ylab.audit.annotation;

import org.springframework.context.annotation.Import;
import website.ylab.audit.config.AuditConfig;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The EnableXXX option, which adds an audit aspect control to a Spring Boot project
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AuditConfig.class)
public @interface EnableAudit {
}