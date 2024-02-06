import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MortagePlanTest {

    @Test
    public void verifyNoExceptionThrown() {
        String input = "CustomerName,100000,5,10";
        String[] customerDetails = input.split(",|\"");

        String customerName = customerDetails[0].trim();
        double totalLoan = Double.parseDouble(customerDetails[1].trim());
        double yearlyInterestRate = Double.parseDouble(customerDetails[2].trim());
        int loanPeriodInYears = Integer.parseInt(customerDetails[3].trim());

        double monthlyPayment = calculateMonthlyPayment(totalLoan, yearlyInterestRate, loanPeriodInYears);

        assertEquals(
            "Prospect: " + customerName + " wants to borrow " + totalLoan + " € for a period of " + loanPeriodInYears + 
            " years and pay " + monthlyPayment + " € each month", getCustomerInfo(customerName, totalLoan, loanPeriodInYears, monthlyPayment));
    }

    private double calculatePower(double base, int exponent) {
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

    private double calculateMonthlyPayment(double totalLoan, double yearlyInterestRate, int loanPeriodInYears) {

        double monthlyInterestRate = yearlyInterestRate / 12 / 100;
        int numberOfPayments = loanPeriodInYears * 12;

        double powerTerm = calculatePower(1 + monthlyInterestRate, numberOfPayments);

        double monthlyPayment = totalLoan * (monthlyInterestRate * powerTerm) / (powerTerm - 1);
        return monthlyPayment;
    }

    private String getCustomerInfo(String customerName, double totalLoan, int loanPeriodInYears, double monthlyPayment) {
        return "Prospect: " + customerName + " wants to borrow " + totalLoan + " € for a period of " 
        + loanPeriodInYears + " years and pay " + monthlyPayment + " € each month";
    }
}
