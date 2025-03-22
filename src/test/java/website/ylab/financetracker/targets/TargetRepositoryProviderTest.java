package website.ylab.financetracker.targets;

import org.junit.jupiter.api.Test;
import website.ylab.financetracker.out.persistence.TargetRepositoryProvider;

import static org.junit.jupiter.api.Assertions.*;

class TargetRepositoryProviderTest {

    @Test
    void getRepository() {
        assertNotNull(TargetRepositoryProvider.getRepository());
    }
}