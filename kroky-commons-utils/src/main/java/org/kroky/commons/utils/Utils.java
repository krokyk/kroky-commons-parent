package org.kroky.commons.utils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kroky
 */
public class Utils {

    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final NumberFormat TWO_DECIMAL_FORMAT = NumberFormat.getNumberInstance(Locale.ENGLISH);

    static {
        // setup the 2-decimal formatter
        TWO_DECIMAL_FORMAT.setMinimumFractionDigits(2);
        TWO_DECIMAL_FORMAT.setMaximumFractionDigits(2);
    }

    public static String formatDateTime(Object date) {
        return DATE_TIME_FORMAT.format(date);
    }

    public static String formatDate(Object date) {
        return DATE_FORMAT.format(date);
    }

    public static Timestamp stringToTimestamp(String dateStr) {
        try {
            return new Timestamp(DATE_TIME_FORMAT.parse(dateStr).getTime());
        } catch (ParseException ex) {
            LOG.error("Unable to parse the given string: " + dateStr);
        }
        return null;
    }

    public static boolean isEmpty(Object obj) {
        if (obj instanceof String) {
            String str = (String) obj;
            return str == null || str.length() == 0;
        }
        return obj == null;
    }

    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            LOG.warn("Received null or empty string for first letter capitalization. Not capitalizing.");
            return str;
        }
        char capital = str.toUpperCase().charAt(0);
        return capital + str.substring(1);
    }

    public static Timestamp timestamp(Object obj) {
        if (obj instanceof Date) {
            return new Timestamp(((Date) obj).getTime());
        } else if (obj instanceof Timestamp) {
            return (Timestamp) obj;
        } else if (obj instanceof String) {
            return stringToTimestamp((String) obj);
        }
        return null;
    }

    public static String formatTwoDecimal(Object value) {
        return TWO_DECIMAL_FORMAT.format(value);
    }

    public static void prettyPrint(Object[] objects) {
        for (Object obj : objects) {
            System.out.println(obj.toString());
        }
    }

    /**
     * round value up if there is anything else than 0 in the second decimal place
     *
     * @param value
     * @return
     */
    public static double roundUp(double value) {
        return (double) Math.round((value + 0.04) * 10) / 10;
    }

    public static <T extends Comparable<? super T>> List<T> sortAsc(Collection<T> col) {
        List<T> list = new ArrayList<>(col);
        Collections.sort(list);
        return list;
    }

    public static <T extends Comparable<? super T>> List<T> sortDesc(Collection<T> col) {
        List<T> list = sortAsc(col);
        Collections.reverse(list);
        return list;
    }

    private static Calendar c = Calendar.getInstance();

    public static Timestamp getTimestampAtDayPrecision(Timestamp date) {
        c.setTimeInMillis(date.getTime());
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        c.clear();
        c.set(year, month, day);
        return new Timestamp(c.getTimeInMillis());
    }

    public static File getFullFile(Object path) {
        if (path == null) {
            return null;
        }
        return getFullFile(path.toString());
    }

    public static File getFullFile(String path) {
        return new File(getFullPath(path));
    }

    public static File getFullFile(File file) {
        return new File(getFullPath(file));
    }

    public static String getFullPath(String path) {
        return getFullPath(new File(path));
    }

    public static String getFullPath(File file) {
        if (file == null) {
            return null;
        }
        try {
            return file.getCanonicalPath();
        } catch (IOException ex) {
            return file.getAbsolutePath();
        }
    }
}
