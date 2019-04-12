package com.practice.olegtojgildin.crypto.presentation.view.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by olegtojgildin on 12/04/2019.
 */

public class ChartDateFormatter implements IAxisValueFormatter {

    private SimpleDateFormat dateFormat;

    public ChartDateFormatter() {
        dateFormat = new SimpleDateFormat("dd MMM");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // convert from unix time stamp
        long time = (long) value * 1000L;

        return dateFormat.format(new Date(time));
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}