package website.ylab.financetracker.adapter.out.persistence.auth;

import java.util.List;

public interface UserRepository {
    UserEntity create(UserEntity user);
    UserEntity getById(long id);
    UserEntity findByName(String name);
    UserEntity update(UserEntity user);
    UserEntity delete(long id);
    List<UserEntity> getAllUsers();
}
