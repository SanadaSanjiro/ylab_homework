package website.ylab.financetracker.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConvertor {
    private final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private final static Logger logger = LogManager.getLogger(DateConvertor.class);

    public static java.sql.Date JavaDateToSQLDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static java.util.Date SqlStringToJavaUtilDate(String value) {
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
            logger.warn("Error while parsing string " + value + " to java.util.Date");
            return null;
        }
    }

    /**
     * Removes Removes unnecessary time data, ensuring correct date comparisons
     * @param date java.util.Date with time data
     * @return java.util.Date where time is 00:00:00
     */
    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}