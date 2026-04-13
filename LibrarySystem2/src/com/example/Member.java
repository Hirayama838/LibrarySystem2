package com.example;

import java.util.ArrayList;
import java.util.List;

public class Member {

    private String memberId;
    private String name;
    private String email;

    private List<Loan> loans = new ArrayList<>();

    private static final int MAX_BORROW_LIMIT = 5;

    public Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    public String getMemberId() {
        return memberId;
    }

    // ★ 貸出（主役）
    public void borrow(Book book, int days) {

        if (loans.stream().filter(l -> !l.isReturned()).count() >= MAX_BORROW_LIMIT) {
            throw new LibraryException("貸出上限です");
        }

        if (!book.isAvailable()) {
            throw new BookNotAvailableException(book.getIsbn());
        }

        Loan loan = new Loan(book.getIsbn(), memberId, days);
        loans.add(loan);

        book.markBorrowed();
    }

    // ★ 返却
    public void returnBook(Book book) {
        Loan loan = findActiveLoan(book.getIsbn());

        loan.returnLoan();
        book.markReturned();
    }

    // ★ 延長
    public void extendLoan(String isbn, int days) {
        Loan loan = findActiveLoan(isbn);
        loan.extend(days);
    }

    private Loan findActiveLoan(String isbn) {
        return loans.stream()
                .filter(l -> l.getIsbn().equals(isbn))
                .filter(l -> !l.isReturned())
                .findFirst()
                .orElseThrow(() -> new LoanNotFoundException(memberId, isbn));
    }

    public List<Loan> getActiveLoans() {
        return loans.stream().filter(l -> !l.isReturned()).toList();
    }
}