package website.ylab.financetracker.application.port.in.auth;

import website.ylab.financetracker.application.domain.model.auth.UserModel;

public interface LoginUseCase {
    UserModel login(UserModel userModel);
}
