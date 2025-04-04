package website.ylab.financetracker.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggableAspect {
    @Pointcut("@annotation(website.ylab.financetracker.annotations.Loggable)")
    public void annotatedByLoggable() {}

    @Around("annotatedByLoggable()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Calling method " + joinPoint);
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis()-startTime;
        System.out.println("Execution of method " + joinPoint.getSignature()
                + "finished. Execution time is " + endTime + " ms");
        return result;
    }
}