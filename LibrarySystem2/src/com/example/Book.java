package com.example;

public class Book {

	private String isbn;
	private String title;
	private String author;
	private boolean borrowed;

	public Book(String isbn, String title, String author) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.borrowed = false;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public boolean isAvailable() {
		return !borrowed;
	}

	public void markBorrowed() {
		if (borrowed) {
			throw new BookNotAvailableException(isbn);
		}
		borrowed = true;
	}

	public void markReturned() {
		borrowed = false;
	}
}
