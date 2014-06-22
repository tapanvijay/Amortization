package amortization.validation;

import amortization.domain.Loan;

public interface Validator 
{
	public boolean validate(Loan loan);
}
