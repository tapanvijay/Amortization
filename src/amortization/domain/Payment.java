package amortization.domain;

public interface Payment 
{
	
	/**
	 * @return Payment amount for this month.
	 */
	public long getPaymentAmount();
	
	/**
	 * @return Interest payment for this month.
	 */
	public long getInterestAmount();
	
	/** 
	 * @return Principal payment for this month.
	 */
	public long getPrincipalAmount();
	
	/**
	 * @return Get remaining principal after this payment.
	 */
	public long getRemainingPrincipal();
}
