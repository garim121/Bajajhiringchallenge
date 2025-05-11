package com.example.bajajfinserv.service;



import org.springframework.stereotype.Service;

@Service
public class SqlProblemSolver {

    public String solveQuery(String regNo) {
        // Extract the last two digits of the registration number
        String lastTwoDigits = regNo.substring(regNo.length() - 2);
        int lastDigits;
        try {
            lastDigits = Integer.parseInt(lastTwoDigits);
        } catch (NumberFormatException e) {
            // Handle non-numeric registration numbers by using the last numeric characters
            StringBuilder numericPart = new StringBuilder();
            for (int i = regNo.length() - 1; i >= 0 && numericPart.length() < 2; i--) {
                if (Character.isDigit(regNo.charAt(i))) {
                    numericPart.insert(0, regNo.charAt(i));
                }
            }
            lastDigits = numericPart.length() > 0 ? Integer.parseInt(numericPart.toString()) : 0;
        }

        // Check if the last digits are odd or even
        if (lastDigits % 2 != 0) {
            // Odd case - Question 1
            return solveSqlQuestion1();
        } else {
            // Even case - Question 2
            return solveSqlQuestion2();
        }
    }

    private String solveSqlQuestion1() {
        // Solution for Question 1 (odd registration numbers)
        // This would be your solution to the linked SQL problem
        return "SELECT department, COUNT(*) as employee_count\n" +
               "FROM employees\n" +
               "WHERE salary > 50000\n" +
               "GROUP BY department\n" +
               "HAVING COUNT(*) > 10\n" +
               "ORDER BY employee_count DESC;";
    }

    private String solveSqlQuestion2() {
        // Solution for Question 2 (even registration numbers)
        // This would be your solution to the linked SQL problem
        return "SELECT p.product_name, SUM(o.quantity) as total_sold\n" +
               "FROM products p\n" +
               "JOIN order_items o ON p.product_id = o.product_id\n" +
               "GROUP BY p.product_id, p.product_name\n" +
               "ORDER BY total_sold DESC\n" +
               "LIMIT 5;";
    }
}