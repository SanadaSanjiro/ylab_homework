package website.ylab.financetracker.application.domain.service.budget;

import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.domain.model.budget.BudgetModel;
import website.ylab.financetracker.application.domain.service.auth.SessionProvider;
import website.ylab.financetracker.application.domain.service.transaction.TransactionService;
import website.ylab.financetracker.application.port.in.budget.BudgetUseCase;

import java.util.*;

public class BudgetService implements BudgetUseCase {
    private final BudgetDAO dao;
    private final SessionProvider sessionProvider;
    private final TransactionService transactionService;

    public BudgetService(BudgetDAO dao, SessionProvider sessionProvider, TransactionService transactionService) {
        this.dao = dao;
        this.sessionProvider = sessionProvider;
        this.transactionService = transactionService;
    }

    @Override
    public BudgetModel create(BudgetModel budget) {
        UserModel currentUser = sessionProvider.getCurrentUser();
        if (Objects.isNull(currentUser)) {
            return null;
        }
        budget.setUserId(currentUser.getId());
        return dao.create(budget).orElse(null);
    }

    @Override
    public BudgetModel get() {
        UserModel currentUser = sessionProvider.getCurrentUser();
        if (Objects.isNull(currentUser)) return null;
        return dao.getById(currentUser.getId()).orElse(null);
    }

    public BudgetModel getByUserId(long userId) {
        return dao.getById(userId).orElse(null);
    }

    @Override
    public BudgetModel delete() {
        UserModel currentUser = sessionProvider.getCurrentUser();
        if (Objects.isNull(currentUser)) return null;
        return dao.delete(currentUser.getId()).orElse(null);
    }

    public BudgetModel deleteById(Long id) {
        return dao.delete(id).orElse(null);
    }

    @Override
    public boolean isExceeded() {
        UserModel currentUser = sessionProvider.getCurrentUser();
        if (Objects.isNull(currentUser)) {
            return false;
        }
        Optional<BudgetModel> optional = dao.getById(currentUser.getId());
        if (optional.isEmpty()) return false;
        BudgetModel budget = optional.get();
        double monthlyExpenses = transactionService.getMonthlyExpenses();
        return budget.isExceeded(monthlyExpenses);
    }

    public boolean isExceededForUser(long id) {
        Optional<BudgetModel> optional = dao.getById(id);
        if (optional.isEmpty()) return false;
        BudgetModel budget = optional.get();
        double monthlyExpenses = transactionService.getMonthlyExpenses();
        return budget.isExceeded(monthlyExpenses);
    }
}