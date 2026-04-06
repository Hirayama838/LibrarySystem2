package com.example;

public class BookNotFoundException extends LibraryException{
    public BookNotFoundException(String isbn) {
        super("本が見つかりません: " + isbn);
    }
}