package website.ylab.audit.aspects;

import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Arrays;
import java.util.Objects;

/**
 * Provides audit advice functionality.
 */
@Aspect
public class AuditableAspect {
    Logger logger = LogManager.getLogger(this.getClass());

    @Pointcut("@annotation(website.ylab.audit.annotation.Auditable)")
    public void annotatedByAuditable() {}

    /**
     * Audits user calls to methods marked with the @Auditable annotation
     * @param joinPoint roceedingJoinPoint
     * @return result of calling a base class method
     * @throws Throwable some exceptions
     */
    @Around("annotatedByAuditable()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        Object par = getParameterByName(joinPoint, "session");
        String username = "";
        if (Objects.nonNull(par)) {
            HttpSession session = (HttpSession) par;
            username = (String) session.getAttribute("username");
            logger.info("User {} called {}", username, joinPoint.getSignature().getName());
        }
        Object result = joinPoint.proceed();
        logger.info("User {} get result {}", username, result.toString());
        return result;
    }

    private Object getParameterByName(ProceedingJoinPoint proceedingJoinPoint, String parameterName) {
        MethodSignature methodSig = (MethodSignature) proceedingJoinPoint.getSignature();
        Object[] args = proceedingJoinPoint.getArgs();
        String[] parametersName = methodSig.getParameterNames();
        int idx = Arrays.asList(parametersName).indexOf(parameterName);
        if(args.length > idx) {
            return args[idx];
        }
        return null;
    }
}
