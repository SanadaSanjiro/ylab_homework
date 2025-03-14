package website.ylab.financetracker.adapter.out.persistence.auth;

import java.util.*;

public class UserRepositoryImplementation implements UserRepository {
    private final Map<Long, UserEntity> users = new HashMap<>();
    private long counter=0L;

    @Override
    public UserEntity create(UserEntity user) {
        user.setId(++counter);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public UserEntity getById(long id) {
        return users.get(id);
    }

    @Override
    public UserEntity findByName(String name) {
        Optional<UserEntity> optional = users.values().stream().filter(u->u.getUsername().equals(name)).findFirst();
        return optional.orElse(null);
    }

    @Override
    public UserEntity update(UserEntity user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public UserEntity delete(long id) {
        return users.remove(id);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return users.values().stream().toList();
    }
}