package org.kroky.commons.swing.tables;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

class Formats {
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final NumberFormat TWO_DECIMAL_FORMAT = NumberFormat.getNumberInstance(Locale.ENGLISH);

    static {
        // setup the 2-decimal formatter
        TWO_DECIMAL_FORMAT.setMinimumFractionDigits(2);
        TWO_DECIMAL_FORMAT.setMaximumFractionDigits(2);
    }
}
