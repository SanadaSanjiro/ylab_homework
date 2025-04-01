package website.ylab.financetracker.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
}