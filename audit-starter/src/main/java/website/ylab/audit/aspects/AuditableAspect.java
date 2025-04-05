package website.ylab.audit.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AuditableAspect {
    Logger logger = LogManager.getLogger(this.getClass());

    @Pointcut("@annotation(website.ylab.audit.annotation.Auditable)")
    public void annotatedByAuditable() {}

    @Around("annotatedByAuditable()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Audit started");
        Object result = joinPoint.proceed();
        logger.info("Audit ended");
        return result;
    }
}
