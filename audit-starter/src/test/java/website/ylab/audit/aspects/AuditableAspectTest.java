package website.ylab.audit.aspects;

import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditableAspectTest {
    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private Logger logger;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AuditableAspect aspect;

    @Test
    void logging() throws Throwable {
        when(joinPoint.proceed()).thenReturn("done");
        when(joinPoint.getSignature()).thenReturn(getSignature());
        when(joinPoint.getArgs()).thenReturn(new Object[]{session});
        when(session.getAttribute(Mockito.anyString())).thenReturn("admin");
        aspect.logging(joinPoint);
        verify(joinPoint, times(1)).proceed();
        verify(logger, times(2)).info(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString());
    }

    private Signature getSignature() {
        return new MethodSignature() {
            @Override
            public Class getReturnType() {
                return null;
            }

            @Override
            public Method getMethod() {
                return null;
            }

            @Override
            public Class[] getParameterTypes() {
                return new Class[0];
            }

            @Override
            public String[] getParameterNames() {
                return new String[] {"session"};
            }

            @Override
            public Class[] getExceptionTypes() {
                return new Class[0];
            }

            @Override
            public String toShortString() {
                return "Method";
            }

            @Override
            public String toLongString() {
                return "Method";
            }

            @Override
            public String getName() {
                return "Method";
            }

            @Override
            public int getModifiers() {
                return 0;
            }

            @Override
            public Class getDeclaringType() {
                return null;
            }

            @Override
            public String getDeclaringTypeName() {
                return "Method";
            }
        };
    }
}