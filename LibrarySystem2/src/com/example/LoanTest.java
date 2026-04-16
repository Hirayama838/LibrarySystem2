package com.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LoanTest {
	
	@Test
	void 返却するとreturnedがtrueになる() {
		Loan loan = new Loan("001", "m001", 14);
		
		loan.returnLoan();
		
		assertTrue(loan.isReturned());
	}
	
	
	void 返却済みを再度返却すると例外() {
		Loan loan = new Loan("001", "m001", 14);
		loan.returnLoan();
		
		assertThrows(LibraryException.class, loan::returnLoan);
	}
	
	@Test
	void 延長できる() {
		Loan loan = new Loan("001", "m001", 14);
		
		assertDoesNotThrow(() -> loan.extend(7));
	}
	
	@Test
	void 不正な延長日数は例外() {
		Loan loan = new Loan("001", "m001", 14);
		
		assertThrows(IllegalArgumentException.class, () -> loan.extend(0));
	}

}
