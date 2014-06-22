/**
 * 
 */
package amortization.ratestrategy;

import amortization.domain.Loan;

/**
 * Specifies how interest and monthly payment for loan is 
 * calculated.
 * 
 * @author Tapan Vijay
 *
 */
public class CompoundRateStrategy extends InterestRateStrategy 
{

	@Override
	public long calculateMonthlyPayment(Loan loan) 
	{
		// M = P * (J / (1 - (Math.pow(1/(1 + J), N))));
		//
		// Where:
		// P = Principal
		// I = Interest
		// J = Monthly Interest in decimal form:  I / (12 * 100)
		// N = Number of months of loan
		// M = Monthly Payment Amount
		// 

		// calculate J
		double monthlyInterestRate = loan.getApr() / MONTHLY_INTEREST_DIVISOR;

		// this is (1 + J)^-N
		double tmp = Math.pow(1d + monthlyInterestRate, - loan.getTermMonths());

		// this is 1 / (1 - (Math.pow(1/(1 + J), N))))
		tmp = 1 / (1d -tmp);

		// M = P * (J / (1 - (Math.pow(1/(1 + J), N))));
		double rc = loan.getAmountBorrowed() * monthlyInterestRate * tmp;

		return Math.round(rc);
	}
}
