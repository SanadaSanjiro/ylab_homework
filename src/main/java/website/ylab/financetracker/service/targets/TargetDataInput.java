package website.ylab.financetracker.service.targets;

import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.in.cli.auth.UserAuthService;
import website.ylab.financetracker.in.dto.target.TargetResponse;
import website.ylab.financetracker.in.dto.transaction.TransactionResponse;
import website.ylab.financetracker.service.transactions.TransactionType;

import java.util.*;

/**
 * Prompts the user for information required for target operations
 */
public class TargetDataInput {
    private final Scanner scanner = new Scanner(System.in);
    private final TargetService targetService = ServiceProvider.getTargetService();

    public String setTarget() {
        long userId = UserAuthService.getCurrentUserId();
        if (userId==0L) return "You should log in first";
        double amount=0;
        do {
            try {
                amount = Double.parseDouble(getData(
                        "Enter a new target. It must be greater than zero."));
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Invalid amount");
            }
        } while (amount <= 0);
        TrackerTarget target = new TrackerTarget().setUserId(userId).setAmount(amount);
        return targetService.setTarget(target).toString();
    }

    public String getTarget() {
        long userId = UserAuthService.getCurrentUserId();
        if (userId==0L) return "You should log in first";
        TargetResponse target = targetService.getByUserId(userId);
        if (Objects.isNull(target)) { return "No target found"; }
        double income = getIncome(userId);
        boolean isReached = income > target.getAmount();
        if (isReached) {
            return "Current target = " + target.getAmount() + " reached! Total income: " + income;
        }
        return "Target not reached! Target: " + target.getAmount() + ". Total income: " + income
                + ". Remaining to accumulate: " + (target.getAmount() - income);
    }

    public boolean isReached() {
        long userId = UserAuthService.getCurrentUserId();
        TargetResponse target = targetService.getByUserId(userId);
        double income = getIncome(userId);
        return income > target.getAmount();
    }

    private double getIncome(long userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date startDate = calendar.getTime();

        List<TransactionResponse> transactions = ServiceProvider.getTransactionService().getUserTransaction(userId);
        return transactions.stream()
                .filter(t->t.getDate().after(startDate))
                .filter(t -> t.getType().equals(TransactionType.INCOME.toString()))
                .mapToDouble(TransactionResponse::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
    }

    private String getData(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}