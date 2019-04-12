package com.practice.olegtojgildin.crypto.presentation.view.chart;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by olegtojgildin on 12/04/2019.
 */

public class DateFormatter {

    public static String format(float value) {
        // Convert from unix time stamp
        long time = (long) value * 1000L;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM");
        return dateFormat.format(new Date(time));
    }
}
