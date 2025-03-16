package website.ylab.financetracker.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import website.ylab.financetracker.out.persistence.ram.auth.RamUserRepo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RamUserRepoTest {
    RamUserRepo ramUserRepo = new RamUserRepo();
    String username = "bob";
    String password = "123456";
    String email = "bob@gmail.com";
    Optional<TrackerUser> optional;

    @BeforeEach
    void setUp() {
        TrackerUser user = new TrackerUser(username, email, password);
        user.setRole(Role.USER);
        user.setEnabled(true);
        optional = ramUserRepo.create(user);
    }

    @Test
    void create() {
        assertTrue(optional.isPresent());
        assertEquals(username, optional.get().getUsername());
        assertEquals(email, optional.get().getEmail());
        assertEquals(password, optional.get().getPassword());
    }

    @Test
    void getByName() {
        // Если пользователь существует
        optional = ramUserRepo.getByName(username);
        assertTrue(optional.isPresent());
        assertEquals(username, optional.get().getUsername());
        assertEquals(email, optional.get().getEmail());
        assertEquals(password, optional.get().getPassword());

        // Если пользователь отсутствует
        optional = ramUserRepo.getByName("badName");
        assertFalse(optional.isPresent());
    }

    @Test
    void update() {
        // Проверяем изменение имени пользователя
        String newUsername = "alice";
        TrackerUser oldUser = ramUserRepo.getByName(username).get();
        TrackerUser newUser = new TrackerUser(newUsername, email, password);
        newUser.setId(oldUser.getId());
        optional = ramUserRepo.update(newUser);
        assertTrue(optional.isPresent());
        assertEquals(newUsername, optional.get().getUsername());
        assertEquals(email, optional.get().getEmail());
        assertEquals(password, optional.get().getPassword());

        // Проверяем изменение email
        String newEmail = "alice@gmail.com";
        oldUser = ramUserRepo.getByName(newUsername).get();
        newUser = new TrackerUser(newUsername, newEmail, password);
        newUser.setId(oldUser.getId());
        optional = ramUserRepo.update(newUser);
        assertTrue(optional.isPresent());
        assertEquals(newUsername, optional.get().getUsername());
        assertEquals(newEmail, optional.get().getEmail());
        assertEquals(password, optional.get().getPassword());

        // Проверяем изменение пароля
        String newPass = "654321";
        oldUser = ramUserRepo.getByName(newUsername).get();
        newUser = new TrackerUser(newUsername, newEmail, newPass);
        newUser.setId(oldUser.getId());
        optional = ramUserRepo.update(newUser);
        assertTrue(optional.isPresent());
        assertEquals(newUsername, optional.get().getUsername());
        assertEquals(newEmail, optional.get().getEmail());
        assertEquals(newPass, optional.get().getPassword());
    }

    @Test
    void delete() {
        // Если пользователь отсутствует
        optional = ramUserRepo.delete(new TrackerUser());
        assertFalse(optional.isPresent());

        // Если пользователь найден и был успешно удален
        TrackerUser user = ramUserRepo.getByName(username).get();
        optional = ramUserRepo.delete(user);
        assertTrue(optional.isPresent());
        assertEquals(username, optional.get().getUsername());
        assertFalse(ramUserRepo.getByName(username).isPresent());
    }

    @Test
    void getByNameAllUsers() {
        assertFalse(ramUserRepo.getAllUsers().isEmpty());
    }
}