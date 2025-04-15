package website.ylab.aspects;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


/**
 * Provides runtime logging functionality.
 */
@Aspect
@Log4j2
public class LoggableAspect {

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
        log.info("Calling method {} with parameters {}", joinPoint.getSignature().getName(),
                joinPoint.getArgs());
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis()-startTime;
        log.info("Execution time is {} ms for method {}"
                , endTime, joinPoint.getSignature().getName());
        return result;
    }

    @AfterThrowing(pointcut = "annotatedByLoggable()", throwing = "exception")
    public void exceptionLogging(JoinPoint joinPoint, Throwable exception) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.warn("Exception then calling {} - {}"
                , methodName, exception.getMessage());
    }
}