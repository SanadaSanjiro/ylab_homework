package website.ylab.financetracker.targets;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.auth.TrackerUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RamTargetRepoTest {
    TrackerUser user = new TrackerUser();
    double target = 100;
    TargetRepository targetRepo = new RamTargetRepo();

    @Test
    void setTarget() {
        Optional<Double> optional = targetRepo.setTarget(user, target);
        assertTrue(optional.isPresent());
        assertEquals(target, optional.get());
    }

    @Test
    void getTarget() {
        targetRepo.setTarget(user, target);
        Optional<Double> optional = targetRepo.getTarget(user);
        assertTrue(optional.isPresent());
        assertEquals(target, optional.get());
    }

    @Test
    void deleteTarget() {
        targetRepo.setTarget(user, target);
        Optional<Double> optional = targetRepo.deleteTarget(user);
        assertTrue(optional.isPresent());
        assertEquals(target, optional.get());
        assertFalse(targetRepo.getTarget(user).isPresent());
    }
}