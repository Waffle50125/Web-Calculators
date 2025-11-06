package edu.kirkwood.model;

import java.util.Objects;

/**
 * Represents a fraction with an integer numerator and denominator.
 * This class provides methods for fraction arithmetic, simplification
 * and comparison.
 */
public class Fraction implements Comparable<Fraction> {
    private int numerator;
    private int denominator;

    /**
     * Default constructor
     * Initializes a new fraction to 1/1
     */
    public Fraction() {
        this.numerator = 1;
        this.denominator = 1;
    }

    /**
     * Constructs a fraction with a specified numerator and denominator
     * @param numerator The top part of the fraction
     * @param denominator The bottom part of the fraction
     */
    public Fraction(int numerator, int denominator) {
        setNumerator(numerator);
        setDenominator(denominator); // The denominator should never be zero
    }

    /**
     * Gets the numerator (the top part) of the fraction
     * @return the numerator
     */
    public int getNumerator() {
        return numerator;
    }

    /**
     * Sets the numerator of the fraction.
     *
     * @param numerator the new numerator
     */
    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    /**
     * Gets the denominator of the fraction.
     *
     * @return the denominator
     */
    public int getDenominator() {
        return denominator;
    }

    /**
     * Sets the denominator of the fraction.
     *
     * @param denominator the new denominator
     * @throws ArithmeticException if the denominator is zero
     */
    public void setDenominator(int denominator) {
        if(denominator == 0) {
            throw new ArithmeticException("Denominator cannot be zero");
        }
        if(denominator < 0){
            numerator *= -1;
            denominator = Math.abs(denominator);
        }
        this.denominator = denominator;
    }

    /**
     * Returns a string representation of the fraction in the format "numerator/denominator".
     *
     * @return a string representation of the fraction
     */
    @Override
    public String toString() {
        return toMixedNumber(numerator, denominator);
    }

    /**
     * Compares this fraction to another fraction.
     *
     * @param o the other Fraction to be compared.
     * @return a negative integer, zero, or a positive integer.
     */
    @Override
    public int compareTo(Fraction o) {
        int a = numerator * o.denominator;
        int b = denominator * o.numerator;
        return Integer.compare(a, b);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fraction fraction = (Fraction) o;
        simplify();
        fraction.simplify();
        return numerator == fraction.numerator && denominator == fraction.denominator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    /**
     * Calculates the greatest common divisor (GCD) of two integers.
     *
     * @param a the first integer
     * @param b the second integer
     * @return the greatest common divisor of a and b
     */
    public static int gcd(int a, int b) {
        // Source: https://stackoverflow.com/a/4009247/6629315
        if (b == 0) {
            return Math.abs(a); // Source: https://stackoverflow.com/a/30693436/6629315
        }
        return gcd(b,a % b);
    }

    /**
     * Calculates the least common multiple (LCM) of two integers.
     *
     * @param a the first integer
     * @param b the second integer
     * @return the least common multiple of a and b
     */
    public static int lcm(int a, int b) {
        // Source: https://stackoverflow.com/a/4202114/6629315
        if(a == 0 || b == 0) {
            return 0;
        }
        return a * (b / gcd(a, b));
    }

    /**
     * Simplifies this fraction to its lowest terms by dividing the numerator
     * and denominator by their greatest common divisor.
     */
    /**
     * Reduces fraction to the lowest terms
     */
    public void simplify() {
        int gcd = gcd(this.numerator, this.denominator);
        numerator /= gcd;
        denominator /= gcd;
    }

    /**
     * Converts this fraction to a mixed number string representation (e.g., "1 2/3").
     * If the fraction is a proper fraction, it returns the fraction itself.
     *
     * @return a string representation of the fraction as a mixed number
     */
    public static String toMixedNumber(int a, int b) {
        Fraction f1 = new Fraction(a, b); // set the Fraction object
        f1.simplify();
        int wholeNum = f1.getNumerator() /  f1.getDenominator(); // get the whole number
        int numerator = f1.getNumerator() % f1.getDenominator(); // get the new numerator
        String result = "";

        // creates a string with or without the fraction depending on if there is one.
        if (numerator == 0) {
            result = wholeNum + "";
        } else {
            if(wholeNum != 0) {
                result = wholeNum + " " + numerator + "/" + f1.getDenominator();
            } else {
                result = numerator + "/" + f1.getDenominator();
            }
        }

        return result;
    }

    /**
     * Adds another fraction to this fraction.
     *
     * @param other the fraction to add
     * @return a new Fraction object representing the sum
     */
    public Fraction add(Fraction other) {
        int newNumerator = this.numerator * other.denominator + this.denominator * other.numerator;
        int newDenominator = this.denominator * other.denominator;
        Fraction result = new Fraction(newNumerator, newDenominator);
        result.simplify();
        return result;
    }

    /**
     * Subtracts another fraction from this fraction.
     *
     * @param other the fraction to subtract
     * @return a new Fraction object representing the difference
     */
    public Fraction subtract(Fraction other) {
        int newNumerator = numerator * other.denominator - denominator * other.numerator;
        int newDenominator = denominator * other.denominator;
        Fraction result =  new Fraction(newNumerator, newDenominator);
        result.simplify();
        return result;
    }

    /**
     * Multiplies this fraction by another fraction.
     *
     * @param other the fraction to multiply by
     * @return a new Fraction object representing the product
     */
//    public Fraction multiply(Fraction other) {
//        int newNumerator = this.numerator * other.numerator;
//        int newDenominator = this.denominator * other.denominator;
//        Fraction result = new Fraction(newNumerator, newDenominator);
//        result.simplify();
//        return result;
//    }
    public Fraction multiply(Fraction other) {
        int newNumerator = this.numerator * other.numerator;
        int newDenominator = this.denominator * other.denominator;
        Fraction result = new Fraction(newNumerator, newDenominator);
        if (result.getDenominator() < 0) {
            result.setDenominator(result.getDenominator() * -1);
            result.setNumerator(result.getNumerator() * -1);
        }
        result.simplify();
        return result;
    }

    /**
     * Divides this fraction by another fraction.
     *
     * @param other the fraction to divide by (the divisor)
     * @return a new Fraction object representing the quotient
     * @throws IllegalArgumentException if the divisor is zero
     */
    public Fraction divide(Fraction other) {
        Fraction result = new Fraction();
        result.setNumerator(numerator * other.denominator);
        result.setDenominator(denominator * other.numerator);
        result.simplify();
        return result;
    }















}
