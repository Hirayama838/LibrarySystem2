package com.example;

import java.util.ArrayList;
import java.util.List;

public class LoanManager implements LoanRepository {

    private List<Loan> loans = new ArrayList<>();

    @Override
    public void save(Loan loan) {
        loans.add(loan);
    }

    @Override
    public List<Loan> findAll() {
        return new ArrayList<>(loans);
    }
}
