package website.ylab.financetracker.application.domain.service.transaction;

import website.ylab.financetracker.application.domain.model.auth.UserModel;
import website.ylab.financetracker.application.domain.model.budget.BudgetModel;
import website.ylab.financetracker.application.domain.model.transaction.TransactionModel;
import website.ylab.financetracker.application.domain.service.auth.SessionProvider;
import website.ylab.financetracker.application.port.in.transaction.TransactionCrudUseCase;
import website.ylab.financetracker.transactions.TransactionType;

import java.util.*;

public class TransactionService implements TransactionCrudUseCase {
    private final TransactionDAO dao;
    private final TransactionFilterer filterer;
    private final SessionProvider sessionProvider;

    public TransactionService(TransactionDAO dao, TransactionFilterer filterer, SessionProvider sessionProvider) {
        this.dao = dao;
        this.filterer = filterer;
        this.sessionProvider = sessionProvider;
    }

    @Override
    public TransactionModel create(TransactionModel transactionModel) {
        UserModel currentUser = sessionProvider.getCurrentUser();
        if (Objects.isNull(currentUser)) {
            return null;
        }
        transactionModel.setUserId(currentUser.getId());
        return dao.create(transactionModel).orElse(null);
    }

    @Override
    public TransactionModel delete(long id) {
        UserModel currentUser = sessionProvider.getCurrentUser();
        if (Objects.isNull(currentUser)) return null;
        if (!(id == currentUser.getId())) return null;
        return dao.delete(id).orElse(null);
    }

    @Override
    public List<TransactionModel> show(Date dateFilter, String categoryFilter, TransactionType typeFilter) {
        UserModel currentUser = sessionProvider.getCurrentUser();
        if (Objects.isNull(currentUser)) return null;
        List<TransactionModel> resultList = dao.getByUser(currentUser.getId());
        resultList = filterer.filter(resultList, dateFilter);
        resultList = filterer.filter(resultList, categoryFilter);
        return filterer.filter(resultList, typeFilter);
    }

    @Override
    public TransactionModel update(TransactionModel transactionModel) {
        UserModel currentUser = sessionProvider.getCurrentUser();
        if (Objects.isNull(currentUser)) return null;
        if (!(transactionModel.getUserId() == currentUser.getId())) return null;
        return dao.update(transactionModel).orElse(null);
    }

    public double getMonthlyExpenses() {
        UserModel currentUser = sessionProvider.getCurrentUser();
        if (Objects.isNull(currentUser)) {
            return 0.0;
        }
        Date startDate = getDateMonthAgo();
        List<TransactionModel> list = this.show(null,
                null, TransactionType.EXPENSE);
        return list.stream()
                .filter(t->t.getDate().after(startDate))
                .mapToDouble(TransactionModel::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
    }

    public double getMonthlyIncome() {
        UserModel currentUser = sessionProvider.getCurrentUser();
        if (Objects.isNull(currentUser)) {
            return 0.0;
        }
        Date startDate = getDateMonthAgo();
        List<TransactionModel> list = this.show(null,
                null, TransactionType.INCOME);
        return list.stream()
                .filter(t->t.getDate().after(startDate))
                .mapToDouble(TransactionModel::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
    }

    private Date getDateMonthAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }
}