package website.ylab.financetracker.application.domain.service.auth;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import website.ylab.financetracker.application.domain.model.auth.UserModel;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserDataServiceTest {
    long id =1L;
    String name = "TestUser";
    String email = "test@test.com";
    String pass = "testPassword";
    String uniqueName = "uniqueName";
    String uniqueEmail ="unique@Email.com";
    String newPass = "newPass";
    UserModel user = createUser();
    UserDAO userDAO;
    UserDataValidator validator = Mockito.mock(UserDataValidator.class);

    @Test
    void changeUserThatNotExists() {
        //Изменяемый пользователь не существует, сервис возвращает null
        userDAO = Mockito.mock(UserDAO.class);
        Mockito.when(userDAO.getUserById(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(validator.isUniqueEmail(Mockito.any())).thenReturn(true);
        Mockito.when(validator.isUniqueName(Mockito.any())).thenReturn(true);
        UserDataService dataService = new UserDataService(userDAO, validator);
        assertNull(dataService.changeUser(user));
    }

    @Test
    void changeUserNotUniqueName() {
        //Новое имя пользователя не уникально, сервис возвращает null
        user = createUser();
        userDAO = Mockito.mock(UserDAO.class);
        Mockito.when(userDAO.getUserById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(userDAO.getAllUsers()).thenReturn(List.of(user));
        UserModel newUserData = new UserModel().setUsername(name).setEmail(uniqueEmail).setPassword("");
        Mockito.when(validator.isUniqueEmail(Mockito.any())).thenReturn(true);
        Mockito.when(validator.isUniqueName(Mockito.any())).thenReturn(false);
        UserDataService dataService = new UserDataService(userDAO, validator);
        assertNull(dataService.changeUser(newUserData));
    }

    @Test
    void changeUserNotUniqueEmail() {
        //Новый email пользователя не уникален, сервис возвращает null
        user = createUser();
        userDAO = Mockito.mock(UserDAO.class);
        Mockito.when(userDAO.getUserById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(userDAO.getAllUsers()).thenReturn(List.of(user));
        UserModel newUserData= new UserModel().setUsername(uniqueName).setEmail(email).setPassword(pass);
        Mockito.when(validator.isUniqueEmail(Mockito.any())).thenReturn(false);
        Mockito.when(validator.isUniqueName(Mockito.any())).thenReturn(true);
        UserDataService dataService = new UserDataService(userDAO, validator);
        assertNull(dataService.changeUser(newUserData));
    }

    @Test
    void changeAllUsersData() {
        //Новые данные пользователя соответствуют всем критериям, пользователь успешно изменен
        user = createUser();
        userDAO = Mockito.mock(UserDAO.class);
        Mockito.when(userDAO.getUserById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(userDAO.getAllUsers()).thenReturn(List.of(user));
        UserModel newUserData= new UserModel().setUsername(uniqueName).setEmail(uniqueEmail).setPassword(newPass);
        Mockito.when(userDAO.changeUser(user)).thenReturn(Optional.of(newUserData));
        Mockito.when(validator.isUniqueEmail(Mockito.any())).thenReturn(true);
        Mockito.when(validator.isUniqueName(Mockito.any())).thenReturn(true);
        UserDataService dataService = new UserDataService(userDAO, validator);
        UserModel changed =  dataService.changeUser(newUserData);
        assertNotNull(changed);
        assertEquals(uniqueName.toLowerCase(), changed.getUsername());
        assertEquals(uniqueEmail.toLowerCase(), changed.getEmail());
        assertEquals(newPass, changed.getPassword());
    }

    @Test
    void changeNameOnly() {
        //Изменяется только имя пользователя, пользователь успешно изменен
        user = createUser();
        userDAO = Mockito.mock(UserDAO.class);
        Mockito.when(userDAO.getUserById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(userDAO.getAllUsers()).thenReturn(List.of(user));
        UserModel newUserData= new UserModel().setUsername(uniqueName).setEmail("").setPassword("");
        Mockito.when(userDAO.changeUser(user)).thenReturn(Optional.of(
                new UserModel().setUsername(uniqueName).setEmail(email).setPassword(pass)));
        Mockito.when(validator.isUniqueEmail(Mockito.any())).thenReturn(true);
        Mockito.when(validator.isUniqueName(Mockito.any())).thenReturn(true);
        UserDataService dataService = new UserDataService(userDAO, validator);
        UserModel changed =  dataService.changeUser(newUserData);
        assertNotNull(changed);
        assertEquals(uniqueName.toLowerCase(), changed.getUsername());
        assertEquals(email.toLowerCase(), changed.getEmail());
        assertEquals(pass, changed.getPassword());
    }

    @Test
    void changeEmailOnly() {
        //Изменяется только имя пользователя, пользователь успешно изменен
        user = createUser();
        userDAO = Mockito.mock(UserDAO.class);
        Mockito.when(userDAO.getUserById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(userDAO.getAllUsers()).thenReturn(List.of(user));
        UserModel newUserData= new UserModel().setUsername("").setEmail(uniqueEmail).setPassword("");
        Mockito.when(userDAO.changeUser(user)).thenReturn(Optional.of(
                new UserModel().setUsername(name).setEmail(uniqueEmail).setPassword(pass)));
        Mockito.when(validator.isUniqueEmail(Mockito.any())).thenReturn(true);
        Mockito.when(validator.isUniqueName(Mockito.any())).thenReturn(true);
        UserDataService dataService = new UserDataService(userDAO, validator);
        UserModel changed =  dataService.changeUser(newUserData);
        assertNotNull(changed);
        assertEquals(name.toLowerCase(), changed.getUsername());
        assertEquals(uniqueEmail.toLowerCase(), changed.getEmail());
        assertEquals(pass, changed.getPassword());
    }

    @Test
    void changePasswordOnly() {
        //Изменяется только имя пользователя, пользователь успешно изменен
        user = createUser();
        userDAO = Mockito.mock(UserDAO.class);
        Mockito.when(userDAO.getUserById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(userDAO.getAllUsers()).thenReturn(List.of(user));
        UserModel newUserData= new UserModel().setUsername("").setEmail("").setPassword(newPass);
        Mockito.when(userDAO.changeUser(user)).thenReturn(Optional.of(
                new UserModel().setUsername(name).setEmail(uniqueEmail).setPassword(pass)));
        Mockito.when(validator.isUniqueEmail(Mockito.any())).thenReturn(true);
        Mockito.when(validator.isUniqueName(Mockito.any())).thenReturn(true);
        UserDataService dataService = new UserDataService(userDAO, validator);
        UserModel changed =  dataService.changeUser(newUserData);
        assertNotNull(changed);
        assertEquals(name.toLowerCase(), changed.getUsername());
        assertEquals(uniqueEmail.toLowerCase(), changed.getEmail());
        assertEquals(pass, changed.getPassword());
    }

    @Test
    void deleteUserNotExists() {
        //Изменяемый пользователь не существует, сервис возвращает null
        userDAO = Mockito.mock(UserDAO.class);
        Mockito.when(userDAO.getUserById(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(validator.isUniqueEmail(Mockito.any())).thenReturn(true);
        Mockito.when(validator.isUniqueName(Mockito.any())).thenReturn(true);
        UserDataService dataService = new UserDataService(userDAO, validator);
        assertNull(dataService.deleteUser(id));
    }

    @Test
    void deleteUserSuccess() {
        //Изменяемый пользователь не существует, сервис возвращает null
        userDAO = Mockito.mock(UserDAO.class);
        Mockito.when(userDAO.getUserById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(userDAO.deleteUser(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(validator.isUniqueEmail(Mockito.any())).thenReturn(true);
        Mockito.when(validator.isUniqueName(Mockito.any())).thenReturn(true);
        UserDataService dataService = new UserDataService(userDAO, validator);
        assertNotNull(dataService.deleteUser(id));
    }

    private UserModel createUser() {
        UserModel user = new UserModel();
        return user.setUsername(name).setPassword(pass).setEmail(email);
    }
}