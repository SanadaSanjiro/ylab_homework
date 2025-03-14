package website.ylab.financetracker.adapter.in.transaction;

import website.ylab.financetracker.application.domain.model.transaction.TransactionModel;
import website.ylab.financetracker.application.port.in.transaction.TransactionCrudUseCase;
import website.ylab.financetracker.transactions.TransactionType;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TransactionAdapter {
    private  final TransactionCrudUseCase crudCase;
    private final TransactionCLI cli;
    private final FilterInterface filter;

    public TransactionAdapter(TransactionCrudUseCase crudCase, TransactionCLI cli, FilterInterface filter) {
        this.crudCase = crudCase;
        this.cli = cli;
        this.filter = filter;
    }

    public String createNewTransaction() {
        TransactionType type = cli.getType();
        double amount = cli.getAmount();
        String category = cli.getCategory();
        Date date = cli.getDate();
        String description = cli.getDescription();
        TransactionModel model = crudCase.create(new TransactionModel()
                .setType(type)
                .setAmount(amount)
                .setCategory(category)
                .setDate(date)
                .setDescription(description));
        return Objects.isNull(model) ? "transaction creation error" : model.toString();
    }

    public String updateTransaction() {
        long id = cli.getId();
        double amount = cli.getAmount();
        String category = cli.getCategory();
        String description = cli.getDescription();
        TransactionModel model = crudCase.update(new TransactionModel()
                .setId(id)
                .setAmount(amount)
                .setCategory(category)
                .setDescription(description));
        return Objects.isNull(model) ? "transaction update error" : model.toString();
    }

    public String deleteTransaction() {
        long id = cli.getId();
        TransactionModel model = crudCase.delete(id);
        return Objects.isNull(model) ? "transaction update error" : model.toString();
    }

    public String getTransactions() {
        Date dateFilter = filter.getDateFilter();
        String categoryFilter = filter.getCategoryFilter();
        TransactionType typeFilter = filter.getTypeFilter();
        List<TransactionModel> result = crudCase.show(dateFilter, categoryFilter, typeFilter);
        return result.toString();
    }
}