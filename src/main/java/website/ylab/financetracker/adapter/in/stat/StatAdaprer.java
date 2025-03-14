package website.ylab.financetracker.adapter.in.stat;

import website.ylab.financetracker.application.port.in.stat.StatUseCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class StatAdaprer {
    private final StatUseCase statService;
    private final Scanner scanner;

    public StatAdaprer(Scanner scanner, StatUseCase statService) {
        this.scanner = scanner;
        this.statService = statService;
    }

    public String getBalance() {
        return statService.getBalance();
    }

    public String getTurnover() {
        System.out.println("Enter the starting date for calculating the turnover");
        Date date = getDate();
        return statService.getTurnover(date);
    }

    public String getCategory() {
        return statService.expensesByCategory();
    }

    public String getReport() {
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