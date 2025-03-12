package website.ylab.financetracker.targets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TargetRepositoryProviderTest {

    @Test
    void getRepository() {
        assertNotNull(TargetRepositoryProvider.getRepository());
    }
}