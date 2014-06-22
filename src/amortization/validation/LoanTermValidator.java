/**
 * 
 */
package amortization.validation;

import amortization.domain.Loan;

/**
 * Loan term validator ensures that all the loan terms are within range for
 * processing.
 * 
 * @author Tapan Vijay
 *
 */
public class LoanTermValidator implements Validator 
{
	private static final long[] AMOUNT_RANGE = new long[] {1, 100000000000l};
	private static final double[] APR_RANGE  = new double[] {0.000001d, 100d};
	private static final int[]    TERM_RANGE = new int[] { 1, 1000000 };
	
	@Override
	public boolean validate(Loan loan) 
	{
		boolean isValid = isValidBorrowAmount(loan.getCurrency().convertToMajor(loan.getAmountBorrowed()));
		isValid 	   &= isValidAPRValue(loan.getApr());
		isValid        &= isValidTerm(loan.getTermMonths());
		return isValid;
	}
	
	public boolean isValidBorrowAmount(double amount) 
	{
		boolean isValid = AMOUNT_RANGE[0] <= amount && amount <= AMOUNT_RANGE[1];
		
		if (!isValid) {
			System.err.println("Please enter amount in range " + 
				AMOUNT_RANGE[0] + " and " + 
				AMOUNT_RANGE[1] + ". ");
		}
		
		return isValid;
	}
	
	public boolean isValidAPRValue(double rate) 
	{
		boolean isValid = APR_RANGE[0] <= rate && rate <= APR_RANGE[1];
		
		if (!isValid) {
			System.err.println("Please enter apr in range " + 
				APR_RANGE[0] + " and " + APR_RANGE[1] + ". ");
		}
		
		return isValid;
	}
	
	public boolean isValidTerm(int months) 
	{
		boolean isValid = TERM_RANGE[0] <= months && months <= TERM_RANGE[1];
		
		if (!isValid) {
			System.err.println("Please enter integer months in range " + 
				TERM_RANGE[0] + " and " + TERM_RANGE[1] + ". ");
		}
		
		
		return isValid;
	}
}
