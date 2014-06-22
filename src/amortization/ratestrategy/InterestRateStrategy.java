package amortization.ratestrategy;

import java.util.ArrayList;
import java.util.List;

import amortization.domain.AmortizationPayment;
import amortization.domain.Loan;
import amortization.domain.Payment;

/**
 * Defines strategy on how interest rate should be applied on loan. Subclass
 * implements his interface to define how interest is applied on principal
 * and calculate list of payments required to settle the loan. Examples
 * are simple interest rate OR compound interest rate.
 * 
 * @author Tapan Vijay
 */
public abstract class InterestRateStrategy 
{
	public static final double MONTHLY_INTEREST_DIVISOR = 12d * 100d;
	
	/**
	 * Calculate monthly payment for the loan.
	 * 
	 * @param loan Loan containing parameters.
	 * @return Monthly payment for the loan.
	 */
	public abstract long calculateMonthlyPayment(Loan loan);
	
	/**
	 * Calculate payments to payoff the loan.
	 * 
	 * @param loan Loan containing parameters.
	 * @return List of payments to payoff the loan.
	 */
	public List<Payment> getPayments(Loan loan)
	{
		// 
		// To create the amortization table, follow these steps:
		//
		// 1. Calculate H = P x J, this is your current monthly interest.
		// 2. Recalculate M considering remaining principal in account.
		// 3. Calculate P = P - (M - H), this is remaining principal.
		// 4. Loop till P is 0 or max number of payments.
		//

		long balance = loan.getAmountBorrowed();
		
		double monthlyInterestRate = loan.getApr() / MONTHLY_INTEREST_DIVISOR;
		long monthlyPaymentAmount  = calculateMonthlyPayment(loan);
		
		List<Payment> paySchedule = new ArrayList<Payment>(loan.getTermMonths());
		for (int i = 1; i <= loan.getTermMonths() && balance > 0; i++) 
		{
			// Calculate H = P x J, this is your current monthly interest
			long curMonthlyInterest = Math.round((double) balance * monthlyInterestRate);

			// the amount required to payoff the loan
			long curPayoffAmount = balance + curMonthlyInterest;

			// current monthly payment
			long curMonthlyPaymentAmount = monthlyPaymentAmount;

			// In case of last payment, pay entire remaining amount
			if (i == loan.getTermMonths()) {
				curMonthlyPaymentAmount = curPayoffAmount;
			}

			// Calculate Q = P - (M - H), this is the new balance of your 
			// principal of your loan.
			balance = balance - (curMonthlyPaymentAmount - curMonthlyInterest);
			
			Payment payment = new AmortizationPayment(curMonthlyPaymentAmount, 
				curMonthlyInterest, balance);
			
			paySchedule.add(payment);
		}
		
		return paySchedule;
	}
}
