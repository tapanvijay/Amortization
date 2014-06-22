//
// Exercise Details:
// Build an amortization schedule program using Java. 
// 
// The program should prompt the user for
//		the amount he or she is borrowing,
//		the annual percentage rate used to repay the loan,
//		the term, in years, over which the loan is repaid.  
// 
// The output should include:
//		The first column identifies the payment number.
//		The second column contains the amount of the payment.
//		The third column shows the amount paid to interest.
//		The fourth column has the current balance.  The total payment amount and the interest paid fields.
//

package amortization;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import amortization.domain.Currency;
import amortization.domain.Loan;
import amortization.domain.Payment;
import amortization.ratestrategy.CompoundRateStrategy;
import amortization.ratestrategy.InterestRateStrategy;
import amortization.validation.LoanTermValidator;
import amortization.validation.ValidationPipeline;

/**
 * This is the main class for printing amortization schedule. It takes care of 
 * generating amortization schedule for given loan parameters.
 * 
 * @author Tapan Vijay
 */
public class AmortizationSchedule 
{
	private static final String[] USER_PROMPTS = {
		"Please enter the amount in dollars you would like to borrow: ",
		"Please enter the annual percentage rate used to repay the loan: ",
		"Please enter the term, in months, over which the loan is repaid: "
	};
	
	private static final String[] FORMAT_ERRORS = {
		"Incorrect Amount. Amount should be a integer number i.e. 10000",
		"Incorrect Apr. Apr should be a floating number i.e. 5.0",
		"Incorrect Term. Term should be a integer number i.e. 12, 24"
	};
	
	private final Loan loan;
	private final ValidationPipeline validations;
	private final Currency currency;
	private final InterestRateStrategy rateStrategy;
	
	/**
	 * Create an instance of AmortizationSchedule.
	 * 
	 * @param loan Loan for which schedule is to be generated.
	 * @param validations Validations required on loan/schedule.
	 * @param currency Loan currency.
	 * @param rateStrategy Interest rate strategy to be used for schedule generation.
	 */
	public AmortizationSchedule(Loan loan, ValidationPipeline validations, 
								Currency currency, 
								InterestRateStrategy rateStrategy) 
	{
		
		this.loan 		  = loan;
		this.validations  = validations;
		this.currency     = currency;
		this.rateStrategy = rateStrategy;
	}
	
	public boolean validate()
	{
		return validations.validate(loan);
	}
	
	public void outputAmortizationSchedule() 
	{
		System.out.println("\n\nAmortization Schedule: \n");
		
		String formatString = "%1$-20s%2$-20s%3$-20s%4$-20s%5$-20s%6$s\n";
		System.out.printf(formatString, "PaymentNumber", "PaymentAmount", 
			"PaymentInterest", "CurrentBalance", "TotalPayments", 
			"TotalInterestPaid");
		
		formatString = "%1$-20d%2$-20.2f%3$-20.2f%4$-20.2f%5$-20.2f%6$.2f\n";
		 
		// specify how many decimal points are needed in output
		int dp = currency.getDecimalPoint();
		formatString = formatString.replace(".2f", "." + dp + "f");
		
		List<Payment> paySchedule = rateStrategy.getPayments(loan);
		double totalPayments = 0;
		double totalInterestPaid = 0;
		for (int payNumber = 1; payNumber <= paySchedule.size(); payNumber++) 
		{
			Payment payment = paySchedule.get(payNumber -1);
			double paymentAmt =  
				currency.convertToMajor(payment.getPaymentAmount());
			double interestAmt = 
				currency.convertToMajor(payment.getInterestAmount());
			double remainingPrincipal = 
				currency.convertToMajor(payment.getRemainingPrincipal());
			
			totalPayments     += paymentAmt;
			totalInterestPaid += interestAmt;
		
			System.out.printf(formatString, payNumber, paymentAmt, interestAmt,
				remainingPrincipal, totalPayments, totalInterestPaid);
		}
	}
	 
	public static Loan readLoan()
	{
		long   amount  = 0;
		double apr 	   = 0;
		int    months  = 0;
		
		Scanner scanner = new Scanner(System.in);
		for (int i = 0; i < USER_PROMPTS.length; i++) 
		{
			System.out.print(USER_PROMPTS[i]);
			
			try {
				switch (i) {
				case 0:   
					amount = scanner.nextLong();
					break;
				case 1:
					apr    = scanner.nextDouble();
					break;
				case 2:
					months = scanner.nextInt();
					break;
				}
			} catch (InputMismatchException e) {
				System.err.println(FORMAT_ERRORS[i]);
				scanner.close();
				throw e;
			}
		}
		
		scanner.close(); // Close the scanner resource
		Loan loan = new Loan(Currency.USD.convertInMinor(amount), 
			apr, months, Currency.USD);
		
		return loan;
	}
	
	public static void main(String [] args) 
	{
		Loan loan = readLoan();
		
		ValidationPipeline validations = new ValidationPipeline();
		validations.addValidator(new LoanTermValidator());
		
		InterestRateStrategy rateStrategy = new CompoundRateStrategy();
		
		AmortizationSchedule as = new AmortizationSchedule(
			loan, validations, Currency.USD, rateStrategy);
		
		if (as.validate()) {
			as.outputAmortizationSchedule();
		}
	}
}