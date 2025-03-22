package website.ylab.financetracker.targets;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.out.persistence.TargetRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TargetServiceTest {
    TargetRepository repository = Mockito.mock(TargetRepository.class);
    TargetService targetService = new TargetService(repository);
    TrackerUser user = new TrackerUser();
    Double target = 100.0;

    @Test
    void setTarget() {
        Mockito.when(repository.setTarget(user, target)).thenReturn(Optional.of(target));
        assertEquals("Target set to 100.0", targetService.setTarget(user, target));
    }

    @Test
    void getTarget() {
        Mockito.when(repository.getTarget(user)).thenReturn(Optional.of(target));
        assertEquals(target, targetService.getTarget(user));
    }

    @Test
    void deleteTarget() {
        Mockito.when(repository.deleteTarget(user)).thenReturn(Optional.of(target));
        assertEquals("Target successfully deleted", targetService.deleteTarget(user));
    }
}