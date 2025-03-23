package website.ylab.financetracker.service.stat;

import website.ylab.financetracker.service.ServiceProvider;
import website.ylab.financetracker.in.cli.auth.UserAuthService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Requests the user to provide information necessary for statistical and analytical operations.
 */
public class StatDataInput {
    private final Scanner scanner = new Scanner(System.in);
    private static final StatService statService = ServiceProvider.getStatService();


    public String getBalance() {
        long userId = UserAuthService.getCurrentUserId();
        if (userId==0L) return "You should log in first";
        return statService.getBalance(userId);
    }

    public String getTurnover() {
        long userId = UserAuthService.getCurrentUserId();
        if (userId==0L) return "You should log in first";
        System.out.println("Enter the starting date for calculating the turnover");
        Date date = getDate();
        return statService.getTurnover(userId, date);
    }

    public String getCategory() {
        long userId = UserAuthService.getCurrentUserId();
        if (userId==0L) return "You should log in first";
        return statService.expensesByCategory(userId);
    }

    public String getReport() {
        long userId = UserAuthService.getCurrentUserId();
        if (userId==0L) return "You should log in first";
        return statService.getReport(userId);
    }

    private Date getDate() {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        boolean isParseOk = false;
        do {
            String input = getData("Enter the date (dd.MM.yyyy): ");
            try {
                date=formatter.parse(input);
                isParseOk = true;
            } catch (ParseException e) {
                System.out.println("Invalid date");
            }
        } while (!isParseOk);
        return date;
    }

    private String getData(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}
