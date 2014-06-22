/**
 * 
 */
package amortization.domain;

/**
 * Container class for monthly amortization payment.
 * 
 */
public class AmortizationPayment implements Payment 
{
	private final long paymentAmount; 
	private final long interestAmount;
	private final long principalAmount;
	private final long remainingPrincipal;
	
	public AmortizationPayment(long amt, long interestAmt, long remainingPrincipal)
	{
		this.paymentAmount 	 	= amt;
		this.interestAmount  	= interestAmt;
		this.principalAmount 	= paymentAmount - interestAmount; 
		this.remainingPrincipal = remainingPrincipal;
	}

	@Override
	public long getPaymentAmount() 
	{
		return paymentAmount;
	}

	@Override
	public long getInterestAmount() 
	{
		return interestAmount;
	}

	@Override
	public long getPrincipalAmount()
	{
		return principalAmount;
	}	
	
	@Override
	public long getRemainingPrincipal() 
	{
		return remainingPrincipal;
	}

	@Override
	public String toString() 
	{
		return "AmortizationPayment [paymentAmount=" + paymentAmount
			+ ", interestAmount=" + interestAmount + ", principalAmount="
			+ principalAmount + ", remainingPrincipal="
			+ remainingPrincipal + "]";
	}
}
