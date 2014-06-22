/**
 * 
 */
package amortization.domain;

/**
 * Enum which specifies currency.
 * 
 * @author Tapan Vijay
 *
 */
public enum Currency 
{
	USD(100d),
	EUR(100d),
	INR(100d);
	
	private final double minorDenominationMultiplier;
	private final int decimalPoint; // decimal point
	
	private Currency(double minorDenominationMultiplier) {
		this.minorDenominationMultiplier = minorDenominationMultiplier;
		this.decimalPoint = (int) Math.log10(minorDenominationMultiplier);
	}
	
	public int getDecimalPoint()
	{
		return decimalPoint;
	}
	
	/**
	 * Convert amount into minor denomination.
	 * 
	 * @param amount Amount to be converted
	 * @return Converted amount
	 */
	public long convertInMinor(double amount) {
		return Math.round(amount * minorDenominationMultiplier);
	}
	
	/**
	 * Convert amount into major denomination.
	 * 
	 * @param amount Amount to be converted
	 * @return Converted amount
	 */
	public double convertToMajor(long amount) {
		return (double) amount / minorDenominationMultiplier;
	}
	
	public static Currency getCurrency(String currencyStr) {
		for (Currency currency : Currency.values()) 
		{
			if (currency.name().equalsIgnoreCase(currencyStr)) {
				return currency;
			}
		}
		
		return null;
	}
}
