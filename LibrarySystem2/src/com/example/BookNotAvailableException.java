package com.example;

public class BookNotAvailableException extends LibraryException{
	public BookNotAvailableException(String isbn) {
		super("貸出できません: " + isbn);
	}
}
