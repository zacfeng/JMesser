package zac.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTimeUtil {
	
	
    private static final Logger log = LoggerFactory.getLogger(DateTimeUtil.class);

    private static final DateFormat sdfDay = new SimpleDateFormat(
            "yyyyMMdd");
    private static final DateFormat sdfUtc = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final DateFormat sdfMilliSec = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss.SSS");

    static {
        sdfUtc.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    }

    public static Date reFormatWithMinutePrecision(Date input) {
        Calendar out = Calendar.getInstance();
        out.setTime(input);
        out.set(Calendar.MILLISECOND, 0);
        out.set(Calendar.SECOND, 0);
        return out.getTime();
    }

    /**
     * Date format: yyyyMMdd
     * 
     * @param inDate
     * @return
     */
    public static String toFormattedDateString(Date inDate) {
        String out = null;
        out = sdfDay.format(inDate);
        return out;
    }

    public static String parseISO8601Date(Date inDate) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(inDate);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1; // Java Calendar month starts
                                                 // with 0, what a strange bug!
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);

        return buildDateTime(year, month, day, hour, minute, second).toString();
    }

    public static Date parseISO8601String(String dateString) {
        Date out = null;
        try {
            out = sdfUtc.parse(dateString);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        return out;
    }

    // 2014-12-18 00:00:12.072
    public static Date parseString(String input) {
        Date out = null;
        try {
            out = sdfMilliSec.parse(input);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        return out;
    }

    public static Date buildDate(int inYear, int inMonth, int inDay,
            int inHour, int inMinutem, int inSecond) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, inYear);
        c.set(Calendar.MONTH, inMonth - 1);
        c.set(Calendar.DAY_OF_MONTH, inDay);
        c.set(Calendar.HOUR_OF_DAY, inHour);
        c.set(Calendar.MINUTE, inMinutem);
        c.set(Calendar.SECOND, inSecond);
        return c.getTime();
    }

    private static DateTime buildDateTime(int inYear, int inMonth, int inDay,
            int inHour, int inMinutem, int inSecond) {
        Chronology chrono = ISOChronology.getInstanceUTC();
        DateTime dt = new DateTime(inYear, inMonth, inDay, inHour, inMinutem,
                inSecond, 0, chrono);
        return dt;
    }

    public static String shrinkPrecision(String strDate, int calendarPrecision) {
        DateTime out = null;
        Date d = parseISO8601String(strDate);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        switch (calendarPrecision) {
        case Calendar.DAY_OF_MONTH:
            out = buildDateTime(year, month, 0, 0, 0, 0);
            break;
        case Calendar.HOUR_OF_DAY:
            out = buildDateTime(year, month, day, 0, 0, 0);
            break;
        case Calendar.MINUTE:
            out = buildDateTime(year, month, day, hour, minute, 0);
            break;
        case Calendar.SECOND:
            out = buildDateTime(year, month, day, hour, minute, second);
            break;
        }
        return out.toString();
    }

    public static Date theEndOfDay(Date aDate) {
        Date out = null;
        Calendar theEndOfDay = dateToCalendar(aDate);

        int theLatestHourOfDay = theEndOfDay
                .getActualMaximum(Calendar.HOUR_OF_DAY);
        int theLatestMinuteOfDay = theEndOfDay
                .getActualMaximum(Calendar.MINUTE);
        int theLatestSecondOfDay = theEndOfDay
                .getActualMaximum(Calendar.SECOND);
        int thelatestMillisecondOfDay = theEndOfDay
                .getActualMaximum(Calendar.MILLISECOND);

        theEndOfDay.set(Calendar.HOUR_OF_DAY, theLatestHourOfDay);
        theEndOfDay.set(Calendar.MINUTE, theLatestMinuteOfDay);
        theEndOfDay.set(Calendar.SECOND, theLatestSecondOfDay);
        theEndOfDay.set(Calendar.MILLISECOND, thelatestMillisecondOfDay);

        out = theEndOfDay.getTime();
        return out;
    }

    public static Date theStartOfDay(Date aDate) {
        Date out = null;
        Calendar theStartOfDay = dateToCalendar(aDate);

        int theLatestHourOfDay = theStartOfDay
                .getActualMinimum(Calendar.HOUR_OF_DAY);
        int theLatestMinuteOfDay = theStartOfDay
                .getActualMinimum(Calendar.MINUTE);
        int theLatestSecondOfDay = theStartOfDay
                .getActualMinimum(Calendar.SECOND);
        int thelatestMillisecondOfDay = theStartOfDay
                .getActualMinimum(Calendar.MILLISECOND);

        theStartOfDay.set(Calendar.HOUR_OF_DAY, theLatestHourOfDay);
        theStartOfDay.set(Calendar.MINUTE, theLatestMinuteOfDay);
        theStartOfDay.set(Calendar.SECOND, theLatestSecondOfDay);
        theStartOfDay.set(Calendar.MILLISECOND, thelatestMillisecondOfDay);

        out = theStartOfDay.getTime();
        return out;
    }

    public static Calendar dateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
