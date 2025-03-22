package website.ylab.financetracker.stat;

import website.ylab.financetracker.ServiceProvider;
import website.ylab.financetracker.auth.TrackerUser;
import website.ylab.financetracker.auth.UserAuthService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

/**
 * Requests the user to provide information necessary for statistical and analytical operations.
 */
public class StatDataInput {
    private final Scanner scanner = new Scanner(System.in);
    private static final StatService statService = ServiceProvider.getStatService();


    public String getBalance() {
        TrackerUser user = UserAuthService.getCurrentUser();
        if (Objects.isNull(user)) return "You should log in first";
        return statService.getBalance();
    }

    public String getTurnover() {
        TrackerUser user = UserAuthService.getCurrentUser();
        if (Objects.isNull(user)) return "You should log in first";
        System.out.println("Enter the starting date for calculating the turnover");
        Date date = getDate();
        return statService.getTurnover(date);
    }

    public String getCategory() {
        TrackerUser user = UserAuthService.getCurrentUser();
        if (Objects.isNull(user)) return "You should log in first";
        return statService.expensesByCategory();
    }

    public String getReport() {
        TrackerUser user = UserAuthService.getCurrentUser();
        if (Objects.isNull(user)) return "You should log in first";
        return statService.getReport();
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
