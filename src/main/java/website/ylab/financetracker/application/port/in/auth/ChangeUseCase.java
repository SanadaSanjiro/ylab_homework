package website.ylab.financetracker.application.port.in.auth;

import website.ylab.financetracker.application.domain.model.auth.UserModel;

/**
 * Changes user's name, email and password
 */
public interface ChangeUseCase {
    /**
     * It can only change the username, email and password. Changes the values of the saved user fields
     * to the new ones from the input UserModel. If some fields in the input UserModel are empty, these
     * values will not be changed.
     * @param model UserModel. Only username, email and password are taken into account.
     * @return UserModel with new field values or null if such user not found
     */
    UserModel changeUser(UserModel model);
}