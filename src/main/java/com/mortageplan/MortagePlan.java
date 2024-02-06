package com.mortageplan;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MortagePlan {

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("prospects.txt"), StandardCharsets.UTF_8));
            String line;

            if ((line = reader.readLine()) != null) {
            }

            while ((line = reader.readLine()) != null) {
                String[] customerDetails = line.split(",|\"");

                if (customerDetails.length >= 4) {
                    String customerName = customerDetails[0].trim();
                    double totalLoan = Double.parseDouble(customerDetails[1].trim());
                    double yearlyInterestRate = Double.parseDouble(customerDetails[2].trim());
                    int loanPeriodInYears = Integer.parseInt(customerDetails[3].trim());
                    double monthlyPayment = calculateMonthlyPayment(totalLoan, yearlyInterestRate, loanPeriodInYears);

                    System.out.println(
                            "Prospect: " + customerName + " wants to borrow " + String.format("%.2f", totalLoan) +
                                    "€ for a period of " + loanPeriodInYears + " years and pay " + String.format("%.2f", monthlyPayment) + "€ each month");

                    System.out.println("****************************************************************************************************");

                } else {
                    System.err.println("Invalid line: " + line);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double calculatePower(double base, int exponent) {
        if (exponent == 0) {
            return 1;
        }

        double result = 1;
        double baseValue = base;

        if (exponent > 0) {
            for (int i = 1; i < exponent; i++) {
                result *= baseValue;
            }
        } else {
            for (int i = 1; i < -exponent; i++) {
                result /= baseValue;
            }
        }

        return exponent > 0 ? result : 1 / result;
    }

    private static double calculateMonthlyPayment(double totalLoan, double yearlyInterestRate, int loanPeriodInYears) {
        double monthlyInterestRate = yearlyInterestRate / 12 / 100;
        int numberOfPayments = loanPeriodInYears * 12;
        double powerTerm = calculatePower(1 + monthlyInterestRate, numberOfPayments);
        double monthlyPayment = totalLoan * (monthlyInterestRate * powerTerm) / (powerTerm - 1);
        return monthlyPayment;
    }
}
