package util;

import java.text.DecimalFormat;

public class Formatter {
    private static final DecimalFormat df = new DecimalFormat("#,###.00");

    public static String formatCurrency(double value) {
        return "Rp " + df.format(value);
    }
}
