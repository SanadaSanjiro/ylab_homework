package website.ylab.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


/**
 * Provides runtime logging functionality.
 */
@Aspect
public class LoggableAspect {
    Logger logger = LogManager.getLogger(this.getClass());

    @Pointcut("@annotation(website.ylab.aspects.Loggable)")
    public void annotatedByLoggable() {}

    /**
     * Logs the method call and its execution time.
     * @param joinPoint ProceedingJoinPoint
     * @return result of calling a base class method
     * @throws Throwable some exceptions
     */
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