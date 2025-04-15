package website.ylab.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoggableAspectTest {
    @Mock
    private ProceedingJoinPoint joinPoint;

    @InjectMocks
    private LoggableAspect aspect;

    @Test
    void logging() throws Throwable {
        when(joinPoint.proceed()).thenReturn(null);
        when(joinPoint.getSignature()).thenReturn(getSignature());
        aspect.logging(joinPoint);
        verify(joinPoint, times(1)).proceed();
    }
    private Signature getSignature() {
        return new Signature() {
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