package edu.kirkwood.controller;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import static edu.kirkwood.shared.Helpers.getSum;
import static edu.kirkwood.shared.Helpers.isANumber;
import static edu.kirkwood.shared.Helpers.round;

@WebServlet(value = "/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/hello.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get raw data from the form
        String num1 = req.getParameter("num1");
        String num2 = req.getParameter("num2");

        // Set raw data as a request attribute
        req.setAttribute("num1", num1);
        req.setAttribute("num2", num2);

        // Validate the data
        boolean valid = true;
        if (num1 == null || num1.isEmpty()) {
            valid = false;
            req.setAttribute("num1Error", "Please enter a number");
        } else if (!isANumber(num1)) {
            valid = false;
            req.setAttribute("num1Error", "Input is not a number");
        }

        if (num2 == null || num2.isEmpty()) {
            valid = false;
            req.setAttribute("num2Error", "Please enter a number");
        } else if (!isANumber(num2)) {
            valid = false;
            req.setAttribute("num2Error", "Input is not a number");
        }

        // Generate response
        if (valid) {
            double sum = getSum.apply(num1, num2);
            String result = String.format("%s + %s = %s", num1, num2, round(sum, 10));
            req.setAttribute("result", result);
        }

        // Forward response to the JSP
        req.getRequestDispatcher("WEB-INF/hello.jsp").forward(req, resp);
    }


}