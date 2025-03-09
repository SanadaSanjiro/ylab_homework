package website.ylab.financetracker.targets;

import website.ylab.financetracker.ServiceProvider;
import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.auth.UserAuthService;
import website.ylab.financetracker.transactions.TrackerTransaction;
import website.ylab.financetracker.transactions.TransactionType;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Prompts the user for information required for target operations
 */
public class TargetDataInput {
    private final Scanner scanner = new Scanner(System.in);
    private final TargetService targetService = ServiceProvider.getTargetService();

    public String setTarget() {
        TrackerUser user = UserAuthService.getCurrentUser();
        double target=0;
        do {
            try {
                target = Double.parseDouble(getData(
                        "Enter a new target. It must be greater than zero."));
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Invalid amount");
            }
        } while (target <= 0);
        return targetService.setTarget(user, target);
    }

    public String getTarget() {
        TrackerUser user = UserAuthService.getCurrentUser();
        double target = targetService.getTarget(user);
        double income = getIncome(user);
        boolean isReached = income > target;
        if (isReached) {
            return "Current target = " + target + " reached! Total income: " + income;
        }
        return "Target not reached! Target: " + target + ". Total income: " + income
                + ". Remaining to accumulate: " + (target - income);
    }

    public boolean isReached() {
        TrackerUser user = UserAuthService.getCurrentUser();
        double target = targetService.getTarget(user);
        double income = getIncome(user);
        return income > target;
    }

    private double getIncome(TrackerUser user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date startDate = calendar.getTime();

        List<TrackerTransaction> transactions = ServiceProvider.getTransactionService().getAllTransactions();
        return transactions.stream()
                .filter(t->t.getDate().after(startDate))
                .filter(t -> t.getType().equals(TransactionType.INCOME))
                .mapToDouble(TrackerTransaction::getAmount)
                .boxed()
                .reduce(0.0, Double::sum);
    }

    private String getData(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}
