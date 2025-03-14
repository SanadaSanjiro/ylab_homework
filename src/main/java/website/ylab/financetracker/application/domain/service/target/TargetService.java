package website.ylab.financetracker.application.domain.service.target;

import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.domain.model.target.TargetModel;
import website.ylab.financetracker.application.domain.service.auth.SessionProvider;
import website.ylab.financetracker.application.domain.service.transaction.TransactionService;
import website.ylab.financetracker.application.port.in.target.TargetUseCase;

import java.util.Objects;
import java.util.Optional;

public class TargetService implements TargetUseCase {
    private final TargetDAO dao;
    private final SessionProvider sessionProvider;
    private final TransactionService transactionService;

    public TargetService(TargetDAO dao, SessionProvider sessionProvider, TransactionService transactionService) {
        this.dao = dao;
        this.sessionProvider = sessionProvider;
        this.transactionService = transactionService;
    }

    @Override
    public TargetModel create(TargetModel target) {
        UserModel currentUser = sessionProvider.getCurrentUser();
        if (Objects.isNull(currentUser)) {
            return null;
        }
        target.setUserId(currentUser.getId());
        return dao.create(target).orElse(null);
    }

    @Override
    public TargetModel get() {
        UserModel currentUser = sessionProvider.getCurrentUser();
        if (Objects.isNull(currentUser)) return null;
        return dao.getById(currentUser.getId()).orElse(null);
    }

    @Override
    public TargetModel delete() {
        UserModel currentUser = sessionProvider.getCurrentUser();
        if (Objects.isNull(currentUser)) return null;
        return dao.delete(currentUser.getId()).orElse(null);
    }

    @Override
    public boolean isReached() {
        UserModel currentUser = sessionProvider.getCurrentUser();
        if (Objects.isNull(currentUser)) {
            return false;
        }
        Optional<TargetModel> optional = dao.getById(currentUser.getId());
        if (optional.isEmpty()) return false;
        TargetModel target = optional.get();
        double monthlyIncome = transactionService.getMonthlyIncome();
        return target.isReached(monthlyIncome);
    }

    public TargetModel deleteTargetById(long id) {
        return dao.delete(id).orElse(null);
    }
}