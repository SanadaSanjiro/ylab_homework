package website.ylab.financetracker.service.targets;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.out.repository.TargetRepositoryProvider;

import static org.junit.jupiter.api.Assertions.*;

class TargetRepositoryProviderTest {

    @Test
    void getRepository() {
        assertNotNull(TargetRepositoryProvider.getRepository());
    }
}