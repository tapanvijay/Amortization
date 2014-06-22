/**
 * 
 */
package amortization.validation;

import java.util.HashSet;
import java.util.Set;

import amortization.domain.Loan;

/**
 * Validation pipeline to validate loan details
 * 
 * @author Tapan Vijay
 */
public class ValidationPipeline 
{
	private Set<Validator> validators = new HashSet<Validator> ();
	
	public void addValidator(Validator validator) 
	{
		validators.add(validator);
	}
	
	public boolean validate(Loan loan)
	{
		boolean isValid = true;
		for (Validator validator : validators) 
		{
			isValid &= validator.validate(loan);
		}
		
		return isValid;
	}
}
