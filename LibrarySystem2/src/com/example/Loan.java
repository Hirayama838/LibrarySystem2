package com.example;

import java.time.LocalDate;

public class Loan {

    private final String isbn;
    private final String memberId;
    private LocalDate dueDate;
    private boolean returned;

    public Loan(String isbn, String memberId, int days) {
        this.isbn = isbn;
        this.memberId = memberId;
        this.dueDate = LocalDate.now().plusDays(days);
        this.returned = false;
    }

    public String getIsbn() { return isbn; }
    public boolean isReturned() { return returned; }

    public void returnLoan() {
        if (returned) {
            throw new LibraryException("すでに返却済みです");
        }
        returned = true;
    }

    public void extend(int days) {
        if (returned) {
            throw new LibraryException("返却済みは延長不可");
        }
        if (days <= 0) {
            throw new IllegalArgumentException("日数不正");
        }
        dueDate = dueDate.plusDays(days);
    }

    public boolean isOverdue() {
        return !returned && dueDate.isBefore(LocalDate.now());
    }
}