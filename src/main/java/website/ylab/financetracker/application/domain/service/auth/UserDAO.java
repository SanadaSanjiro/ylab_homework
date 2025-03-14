package website.ylab.financetracker.application.domain.service.auth;

import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.port.out.auth.ExternalUserStorage;

import java.util.List;
import java.util.Optional;

public class UserDAO {
    private final ExternalUserStorage storage;

    public UserDAO(ExternalUserStorage storage) {
        this.storage = storage;
    }

    public Optional<UserModel> createUser(UserModel user) {
        return storage.create(user);
    }

    public Optional<UserModel> getUserByName(String name) {
        return storage.findByName(name);
    }

    public Optional<UserModel> getUserById(Long id) {
        return storage.getById(id);
    }

    public Optional<UserModel> updateUser(UserModel user) {
        return storage.update(user);
    }

    public Optional<UserModel> deleteUser(long id) {
        return storage.delete(id);
    }

    public List<UserModel> getAllUsers() {
        return storage.getAllUsers();
    }
}