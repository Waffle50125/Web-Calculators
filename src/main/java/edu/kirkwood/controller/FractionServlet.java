package edu.kirkwood.controller;

import edu.kirkwood.model.Fraction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static edu.kirkwood.shared.Helpers.isANumber;
import static edu.kirkwood.shared.Helpers.isAnInt;

@WebServlet(value="/fraction")
public class FractionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/fraction.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get raw data
        String numerator1 = req.getParameter("numerator1");
        String denominator1 = req.getParameter("denominator1");
        String operator = req.getParameter("operator");
        String numerator2 = req.getParameter("numerator2");
        String denominator2 = req.getParameter("denominator2");

        // Set raw data as attributes
        req.setAttribute("numerator1", numerator1);
        req.setAttribute("denominator1", denominator1);
        req.setAttribute("operator", operator);
        req.setAttribute("numerator2", numerator2);
        req.setAttribute("denominator2", denominator2);

        // validate
        boolean valid = true;
        if (numerator1 == null || numerator1.isEmpty()) {
            valid = false;
            req.setAttribute("numerator1Error", "First numerator is required");
        } else if (!isAnInt(numerator1)) {
            valid = false;
            req.setAttribute("numerator1Error", "First numerator must be an integer");
        }

        if (numerator2 == null || numerator2.isEmpty()) {
            valid = false;
            req.setAttribute("numerator2Error", "Second numerator is required");
        } else if (!isAnInt(numerator2)) {
            valid = false;
            req.setAttribute("numerator2Error", "Second numerator must be an integer");
        }

        String[] validOperators = {"+", "-", "*", "/"};
        boolean isValidOperator = false;
        for (String s : validOperators) {
            // loops through isValidOperator until it finds a match or reaches the end
            // if a match is found, breaks the loop and sets the flag to valid
            // if no match is found, then isValidOperator stays false and valid is set to false
            if (operator.equals(s)) {
                isValidOperator = true;
                break;
            }
        }
        if (!isValidOperator) {
            valid = false;
        }

        if (denominator1 == null || denominator1.isEmpty()) {
            valid = false;
            req.setAttribute("denominator1Error", "First denominator is required");
        } else if (!isAnInt(denominator1)) {
            valid = false;
            req.setAttribute("denominator1Error", "First denominator must be an integer");
        }

        if (denominator2 == null || denominator2.isEmpty()) {
            valid = false;
            req.setAttribute("denominator2Error", "Second denominator is required");
        } else if (!isAnInt(denominator2)) {
            valid = false;
            req.setAttribute("denominator2Error", "Second denominator must be an integer");
        }

        Fraction fraction1 = null;
        Fraction fraction2 = null;
        if (valid) {
            try {
                fraction1 = new Fraction(Integer.parseInt(numerator1), Integer.parseInt(denominator1));
            } catch (ArithmeticException e) {
                req.setAttribute("denominator1Error", "For the first denominator: " + e.getMessage());
                valid = false;
            }

            try {
                fraction2 = new Fraction(Integer.parseInt(numerator2), Integer.parseInt(denominator2));
            } catch (ArithmeticException e) {
                req.setAttribute("denominator2Error", "For the second denominator: " + e.getMessage());
                valid = false;
            }
        }

        // generate response
        if (valid) {
            Fraction fractionResult = new Fraction();
            String resultString = "";
            if (operator.equals("+")) {
                fractionResult = fraction1.add(fraction2);
                resultString = String.format("%s + %s = %s", fraction1.toString(), fraction2.toString(), fractionResult.toString());
            } else if (operator.equals("-")) {
                fractionResult = fraction1.subtract(fraction2);
                resultString = String.format("%s - %s = %s", fraction1.toString(), fraction2.toString(), fractionResult.toString());
            } else if (operator.equals("*")) {
                fractionResult = fraction1.multiply(fraction2);
                resultString = String.format("%s × %s = %s", fraction1.toString(), fraction2.toString(), fractionResult.toString());
            } else if (operator.equals("/")) {
                fractionResult = fraction1.divide(fraction2);
                resultString = String.format("%s ÷ %s = %s", fraction1.toString(), fraction2.toString(), fractionResult.toString());
            }
            req.setAttribute("result", resultString);
        }
        // show those fractions
        req.getRequestDispatcher("WEB-INF/fraction.jsp").forward(req, resp);
    }
}
