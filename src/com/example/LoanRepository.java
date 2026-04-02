package com.example;

import java.util.List;

public interface LoanRepository {
	void save(Loan loan);
	List<Loan> findAll();

}
