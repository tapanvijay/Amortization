/**
 * 
 */
package amortization.domain;

/**
 * This class represents Loan.
 * 
 * @author Tapan Vijay
 *
 */
public class Loan 
{	
	private final long     amountBorrowed; 	 // amount in minor denominations	    
	private final double   apr;              // annual interest rate
	private final int      termMonths; 	     // loan period in months
	private final Currency currency;
	
	/**
	 * Create Loan object.
	 * 
	 * @param amountBorrowed Amount borrowed
	 * @param apr Annual interest rate
	 * @param termMonths Loan period in years
	 * @param currency Currency
	 */
	public Loan(long amountBorrowed, double apr, int termMonths, 
				Currency currency) 
	{
		this.amountBorrowed = amountBorrowed;
		this.apr            = apr;
		this.termMonths 	= termMonths;
		this.currency       = currency;
	}
	
	public long getAmountBorrowed() 
	{
		return amountBorrowed;
	}

	public double getApr() 
	{
		return apr;
	}

	public int getTermMonths() 
	{
		return termMonths;
	}
	
	public Currency getCurrency()
	{
		return currency;
	}
}
