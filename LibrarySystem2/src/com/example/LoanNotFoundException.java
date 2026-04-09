package com.example;

public class LoanNotFoundException extends LibraryException {
    public LoanNotFoundException(String memberId, String isbn) {
        super("貸出が見つかりません: memberId=" + memberId + ", isbn=" + isbn);
    }
}