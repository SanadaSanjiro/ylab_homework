package website.ylab.financetracker.service.targets;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import website.ylab.financetracker.in.dto.target.TargetResponse;
import website.ylab.financetracker.out.repository.TargetRepository;
import website.ylab.financetracker.service.auth.TrackerUser;
import website.ylab.financetracker.service.auth.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TargetServiceTest {
    long id = 1L;
    long userId = 1L;
    double amount = 100.0;

    UserService userService = Mockito.mock(UserService.class);
    TargetRepository repository = Mockito.mock(TargetRepository.class);
    TargetService service = new TargetService(repository, userService);

    @Test
    void setTarget() {
        Mockito.when(userService.getById(Mockito.anyLong())).thenReturn(Optional.of(new TrackerUser()));
        Mockito.when(repository.setTarget(Mockito.any())).thenReturn(Optional.of(testTarget()));
        TargetResponse response = service.setTarget(testTarget());
        assertEquals(id, response.getId());
        assertEquals(userId, response.getUserId());
        assertEquals(amount, response.getAmount());
    }

    @Test
    void getByUserId() {
        Mockito.when(repository.getByUserId(Mockito.anyLong())).thenReturn(Optional.of(testTarget()));
        TargetResponse response = service.getByUserId(userId);
        assertEquals(id, response.getId());
        assertEquals(userId, response.getUserId());
        assertEquals(amount, response.getAmount());
    }

    private TrackerTarget testTarget() {
        return new TrackerTarget().setId(id).setUserId(userId).setAmount(amount);
    }
}