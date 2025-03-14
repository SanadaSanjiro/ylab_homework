package website.ylab.financetracker.application.port.in.stat;

import java.util.Date;

public interface StatUseCase {
    /**
     * Method to get balance information
     * @return String balance (income and expenses)
     */
    String getBalance();

    /**
     * Method to get information about turnover from start date
     * @param startDate start date to count turnover
     * @return String turnover from start date
     */
    String getTurnover(Date startDate);

    /**
     * Method to get information about expenses grouped by categories
     * @return String expenses grouped by categories
     */
    String expensesByCategory();

    /**
     * Method to get report with all data above
     * @return String with a balance, turnover and expenses by categories
     */
    String getReport();
}