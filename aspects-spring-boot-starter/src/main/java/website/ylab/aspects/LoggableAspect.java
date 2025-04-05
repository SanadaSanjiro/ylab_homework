package website.ylab.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;



@Aspect
public class LoggableAspect {
    Logger logger = LogManager.getLogger(this.getClass());

    @Pointcut("@annotation(website.ylab.aspects.Loggable)")
    public void annotatedByLoggable() {}

    @Around("annotatedByLoggable()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Calling method {}", joinPoint.getSignature().getName());
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis()-startTime;
        logger.info("Execution time is {} ms for method {}"
                , endTime, joinPoint.getSignature().getName());
        return result;
    }
}