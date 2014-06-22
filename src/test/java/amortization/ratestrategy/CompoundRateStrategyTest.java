package test.java.amortization.ratestrategy;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import amortization.domain.Currency;
import amortization.domain.Loan;
import amortization.domain.Payment;
import amortization.ratestrategy.CompoundRateStrategy;
import amortization.ratestrategy.InterestRateStrategy;

public class CompoundRateStrategyTest 
{

	private InterestRateStrategy rateStrategy;

	@Before
	public void setUp() throws Exception 
	{
		rateStrategy = new CompoundRateStrategy();
	}

	@Test
	public void testCalculateMonthlyPayment() 
	{
		Loan loan = new Loan(10000000, 10, 5, Currency.USD);
		long monthlyPayment = rateStrategy.calculateMonthlyPayment(loan);
		assertEquals(2050277, monthlyPayment);
		
		loan = new Loan(10000000, 120, 1, Currency.USD);
		monthlyPayment = rateStrategy.calculateMonthlyPayment(loan);
		assertEquals(11000000, monthlyPayment);
	}

	@Test
	public void testGetPayments() 
	{
		Loan loan = new Loan(10000000, 10, 5, Currency.USD);
		List<Payment> payments = rateStrategy.getPayments(loan);
		assertEquals(5, payments.size());
		
		assertEquals(2050277, payments.get(0).getPaymentAmount());
		assertEquals(1966944, payments.get(0).getPrincipalAmount());
		assertEquals(83333,   payments.get(0).getInterestAmount());
		assertEquals(8033056, payments.get(0).getRemainingPrincipal());
		
		assertEquals(2050277, payments.get(1).getPaymentAmount());
		assertEquals(1983335, payments.get(1).getPrincipalAmount());
		assertEquals(66942,   payments.get(1).getInterestAmount());
		assertEquals(6049721, payments.get(1).getRemainingPrincipal());
		
		assertEquals(2050277, payments.get(2).getPaymentAmount());
		assertEquals(1999863, payments.get(2).getPrincipalAmount());
		assertEquals(50414,   payments.get(2).getInterestAmount());
		assertEquals(4049858, payments.get(2).getRemainingPrincipal());
		
		assertEquals(2050277, payments.get(3).getPaymentAmount());
		assertEquals(2016528, payments.get(3).getPrincipalAmount());
		assertEquals(33749,   payments.get(3).getInterestAmount());
		assertEquals(2033330, payments.get(3).getRemainingPrincipal());
		
		assertEquals(2050274, payments.get(4).getPaymentAmount());
		assertEquals(2033330, payments.get(4).getPrincipalAmount());
		assertEquals(16944,   payments.get(4).getInterestAmount());
		assertEquals(0,       payments.get(4).getRemainingPrincipal());
		
		loan = new Loan(10000000, 120, 1, Currency.USD);
		payments = rateStrategy.getPayments(loan);
		assertEquals(1, payments.size());
		
		assertEquals(11000000,  payments.get(0).getPaymentAmount());
		assertEquals(10000000,  payments.get(0).getPrincipalAmount());
		assertEquals(1000000,   payments.get(0).getInterestAmount());
		assertEquals(0,         payments.get(0).getRemainingPrincipal());
	}
}
