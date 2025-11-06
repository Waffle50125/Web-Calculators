package edu.kirkwood.shared;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.function.BiFunction;

public class Helpers {
    // Emilia
    /** This method returns a bool for if a string isn't null or empty.
     * @param str The string needing validation.
     * @return Returns true if string is not null or empty, false if otherwise.
     */
    public static boolean isValidString(String str) {
        return str != null && !str.equals("");
    }

    // Camren
    /*
     * Rounds a decimal number to a specified number of decimal places
     *
     * @param number        the number to round
     * @param numDecPlaces  the number of decimal places to retain
     * @return a rounded number as a string without trailing zeros
     */
    public static String round(double number, int numDecPlaces) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(number));
        bigDecimal = bigDecimal.setScale(numDecPlaces, RoundingMode.HALF_UP).stripTrailingZeros();
        return bigDecimal.toString();
    }

    // Celeste
    /**
     * Formats a given double value into USD
     * @param amt The double value to be formatted
     * @return A String formatted into USD
     */
    public static String toCurrency(double amt) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(amt);
    }

    public static String formatDateLong(LocalDate date) {
        DateTimeFormatter dateFormatOutput = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
        return dateFormatOutput.format(date);
    }

    public static String formatDateShort(LocalDate date) {
        DateTimeFormatter dateFormatOutput = DateTimeFormatter.ofPattern("M/d/yyyy");
        return dateFormatOutput.format(date);
    }

    // Asle and Logan
    /**
     * Function to test if given date is in the past.
     * Takes a date, checks if its null, if not returns true or false based on if date is in past or not
     * @param date Date to be checked
     * @return A boolean, true if date is in past, false if not
     */
    public static boolean isDateInThePast(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return date.isBefore(LocalDate.now()); // Check if the date is before today
    }

    public static boolean isDateInRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        if (date == null || startDate == null || endDate == null) {
            throw new IllegalArgumentException("None of the dates can be null");
        }

        return (date.isEqual(startDate) || date.isAfter(startDate)) &&
                (date.isEqual(endDate) || date.isBefore(endDate));
    }
    public static BiFunction<String, String, Double> getSum = (num1, num2) -> Double.parseDouble(num1) + Double.parseDouble(num2);


    public static boolean isANumber(String str){
        boolean result = false;

        try {
            Double.parseDouble(str);
            result = true;
        } catch (NumberFormatException e) {
            result = false;
        }

        return result;
    }

    public static boolean isAnInt(String str){
        boolean result = false;

        try {
            Integer.parseInt(str);
            result = true;
        } catch (NumberFormatException e) {
            result = false;
        }

        return result;
    }
}