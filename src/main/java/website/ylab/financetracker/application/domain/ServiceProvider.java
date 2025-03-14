package website.ylab.financetracker.application.domain;

import website.ylab.financetracker.adapter.out.persistence.auth.UserEntityMapper;
import website.ylab.financetracker.adapter.out.persistence.auth.UserPersistenceAdapter;
import website.ylab.financetracker.adapter.out.persistence.auth.UserRepository;
import website.ylab.financetracker.adapter.out.persistence.auth.UserRepositoryImplementation;
import website.ylab.financetracker.adapter.out.persistence.budget.*;
import website.ylab.financetracker.application.port.out.auth.ExternalUserStorage;
import website.ylab.financetracker.application.port.out.budget.ExternalBudgetStorage;

public class ServiceProvider {

    private ExternalUserStorage getUserStorage() {
        UserEntityMapper mapper = new UserEntityMapper();
        UserRepository repository = new UserRepositoryImplementation();
        return new UserPersistenceAdapter(repository, mapper);
    }

    private ExternalBudgetStorage getBudgetStorage() {
        BudgetEntityMapper mapper = new BudgetEntityMapper();
        BudgetRepository repository = new BudgetRepositoryImplementation();
        return new BudgetPersistenceAdapter(repository, mapper);
    }


}
